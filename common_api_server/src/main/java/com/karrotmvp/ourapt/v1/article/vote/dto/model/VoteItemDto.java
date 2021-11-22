package com.karrotmvp.ourapt.v1.article.vote.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class VoteItemDto {

  @NotNull
  private String id;
  @NotNull
  private String mainText;
  @NotNull
  private int votedCount = 0;
  @NotNull
  private boolean isMyVote = false;
}
