package com.karrotmvp.ourapt.v1.article.vote;

import com.karrotmvp.ourapt.v1.article.ArticleService;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.request.VoteContentDto;
import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;
import com.karrotmvp.ourapt.v1.article.vote.entity.VoteItem;
import com.karrotmvp.ourapt.v1.article.vote.repository.VoteItemRepository;
import com.karrotmvp.ourapt.v1.article.vote.repository.VoteRepository;
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
public class VoteService extends ArticleService<Vote, VoteDto> {

  private final VoteItemRepository voteItemRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  public VoteService(VoteRepository voteRepository, ModelMapper mapper, VoteItemRepository voteItemRepository, UserRepository userRepository, UserService userService) {
    super(voteRepository, voteRepository, mapper);
    this.voteItemRepository = voteItemRepository;
    this.userRepository = userRepository;
    this.userService = userService;
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

  private boolean checkIfUserVotedFor(String userId, String voteItem) {
    return false;
  }

  @Override
  protected Class<VoteDto> getClassOfDomainModel() {
    return VoteDto.class;
  }
}
