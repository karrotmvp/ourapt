package com.karrotmvp.ourapt.v1.article.vote.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VoteItemDto {

  private String id;
  private String mainText;
  private int votedCount;
  private boolean isMyVote;
}
