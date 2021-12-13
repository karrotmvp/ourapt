package com.karrotmvp.ourapt.v1.article.vote.repository;

import com.karrotmvp.ourapt.v1.article.comment.Comment;
import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.comment.repository.CommentRepository;
import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.FeedDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.FeedItemDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoteFeedService {
  private VoteService voteService;
  private CommentRepository commentRepository;
  private ModelMapper mapper;

  public FeedDto getFeedInApartment(String apartmentId) {
    return joinWithLastComment(this.voteService.getVotesInApartment(apartmentId));
  }

  private FeedDto joinWithLastComment(List<VoteDto> votes) {
    Map<String, Comment> voteIdAndCommentMap = new HashMap<>();
    this.commentRepository.findByParentIdIn(
      votes.stream().map(VoteDto::getId).collect(Collectors.toSet())
    ).forEach((c) -> {
      Comment existing = voteIdAndCommentMap.get(c.getParent().getId());
      if (existing == null) {
        voteIdAndCommentMap.put(c.getParent().getId(), c);
        return;
      }
      voteIdAndCommentMap.put(
        c.getParent().getId(),
        existing.getCreatedAt().getTime() < c.getCreatedAt().getTime() ? c : existing
      );
    });
    return new FeedDto(votes.stream()
      .map(FeedItemDto::new)
      .peek((item) -> {
        String voteId = item.getVote().getId();
        Comment lastComment = voteIdAndCommentMap.getOrDefault(voteId, null);
        item.setLastComment(
          lastComment != null
            ? mapper.map(lastComment, CommentDto.class)
            : null
        );
      })
      .collect(Collectors.toList())
    );
  }


}
