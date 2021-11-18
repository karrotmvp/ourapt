package com.karrotmvp.ourapt.v1.article.vote.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class VoteContentDto {

  @NoArgsConstructor
  @Getter
  @Setter
  public static class VoteItemContentDto {
    private String mainText;
  }

  @NotNull
  @NotEmpty
  private String mainText;

  @NotNull
  private List<VoteItemContentDto> items;
}
