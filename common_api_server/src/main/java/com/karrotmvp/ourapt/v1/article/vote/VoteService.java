package com.karrotmvp.ourapt.v1.article.vote;

import com.karrotmvp.ourapt.v1.article.ArticleBaseService;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.request.VoteContentDto;
import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;
import com.karrotmvp.ourapt.v1.article.vote.entity.VoteItem;
import com.karrotmvp.ourapt.v1.article.vote.entity.Voting;
import com.karrotmvp.ourapt.v1.article.vote.entity.VotingId;
import com.karrotmvp.ourapt.v1.article.vote.repository.VoteItemRepository;
import com.karrotmvp.ourapt.v1.article.vote.repository.VoteRepository;
import com.karrotmvp.ourapt.v1.article.vote.repository.VotingRepository;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class VoteService extends ArticleBaseService<Vote, VoteDto> {

  private final VoteItemRepository voteItemRepository;
  private final VotingRepository votingRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  public VoteService(VoteRepository voteRepository, ModelMapper mapper, VoteItemRepository voteItemRepository, VotingRepository votingRepository, UserRepository userRepository, UserService userService) {
    super(voteRepository, voteRepository, mapper);
    this.voteItemRepository = voteItemRepository;
    this.votingRepository = votingRepository;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  public List<VoteWithWhereCreatedDto> getVotesAndOriginWithOffsetCursor(int perPage, int pageNum) {
    return this.getPageAndOriginWithOffsetCursor(perPage, pageNum, VoteWithWhereCreatedDto.class);
  }


  @Transactional(rollbackFor = RuntimeException.class)
  public VoteDto writeNewVote(VoteContentDto content, String writerId, String regionId) {
    User writer = this.userRepository.findById(writerId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    this.userService.assertUserIsNotBanned(writer);
    Vote created = new Vote();
    created.setWriter(writer);
    created.setMainText(content.getMainText());
    created.setRegionWhereCreated(regionId);
    if (writer.getCheckedIn() == null) {
      throw new NotCheckedInUserException();
    }
    created.setApartmentWhereCreated(writer.getCheckedIn());
    Vote saved = this.articleRepository.save(created);

    List<VoteItem> items = IntStream.range(0, content.getItems().size())
      .mapToObj(i -> new VoteItem(content.getItems().get(i).getMainText(), i))
      .peek(newVote -> newVote.setParent(saved))
      .collect(Collectors.toList());
    this.voteItemRepository.saveAll(items);
    return mapper.map(saved, VoteDto.class);
  }


  @Transactional
  public void addOrUpdateVotingToVoteItem(String userId, String voteItemId) {
    VoteItem itemToVoteFor = this.safelyGetVoteItem(voteItemId);
    List<Voting> alreadyVotingsOfUser = getVotingsOfUserForVote(userId, itemToVoteFor.getParent().getId());

    // 중복 선택 불가 투표
    if (alreadyVotingsOfUser.size() > 0) {
      if (alreadyVotingsOfUser.stream()
        .anyMatch(voting -> voting.getVoteFor().getId().equals(voteItemId))
      ) {
        return;
      }
      this.votingRepository.deleteAll(alreadyVotingsOfUser);
    }

    Voting voting = new Voting();
    User user = userService.safelyGetUserById(userId);
    userService.assertUserIsNotBanned(user);

    voting.setVotedBy(user);
    voting.setVoteFor(itemToVoteFor);
    this.votingRepository.save(voting);
  }

  public void cancelVoting(String userId, String voteItemId) {
    this.votingRepository.deleteById(new VotingId(userId, voteItemId));
  }

  private List<Voting> getVotingsOfUserForVote(String userId, String voteId) {
    List<Voting> votingsForVote = this.getVotingsForVote(voteId);
    return votingsForVote.stream()
      .filter(voting -> voting.getVotedBy().getId().equals(userId)) // Eager load
      .collect(Collectors.toList());
  }

  private List<Voting> getVotingsForVote(String voteId) {
    Vote target = this.safelyGetById(voteId);
    return this.votingRepository.findByVoteForIdIn(
      target.getItems().stream().map(VoteItem::getId).collect(Collectors.toList())
    );
  }

  private VoteItem safelyGetVoteItem(String voteItemId) {
    return voteItemRepository.findById(voteItemId)
      .orElseThrow(() -> new DataNotFoundFromDBException("This voteItemId is invalid ID: " + voteItemId));
  }

  @Override
  protected Class<VoteDto> getClassOfDomainModel() {
    return VoteDto.class;
  }
}
