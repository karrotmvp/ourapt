package com.karrotmvp.ourapt.v1.article.vote.dto.response;

import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class FeedItemDto {
  @NotNull
  private VoteDto vote;
  private CommentDto lastComment;

  public FeedItemDto(VoteDto vote) {
    this.vote = vote;
  }
}
