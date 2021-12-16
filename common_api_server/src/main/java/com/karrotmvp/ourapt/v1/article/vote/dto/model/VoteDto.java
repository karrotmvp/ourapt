package com.karrotmvp.ourapt.v1.article.vote.dto.model;

import com.karrotmvp.ourapt.v1.article.ArticleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class VoteDto extends ArticleDto {
  @NotNull
  private Boolean isInProgress;
  @NotNull
  private List<VoteItemDto> items;
  @NotNull
  private int countOfComments;
}
