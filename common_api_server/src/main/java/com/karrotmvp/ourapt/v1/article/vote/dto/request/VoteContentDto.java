package com.karrotmvp.ourapt.v1.article.vote.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class VoteContentDto {

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class VoteItemContentDto {
    @NotNull
    @NotEmpty
    private String mainText;
  }

  @NotNull
  @NotEmpty
  private String mainText;

  @NotNull
  private List<VoteItemContentDto> items;

  public void trimItems() {
    this.items = this.items.stream()
      .filter(i -> !i.mainText.isEmpty())
      .collect(Collectors.toList());
  }
}
