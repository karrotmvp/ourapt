package com.karrotmvp.ourapt.v1.article.vote.dto.response;

import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;

import java.util.List;

public class FeedItemDetailDto {
  private VoteDto vote;
  private List<CommentItemDto> commentItems;
}
