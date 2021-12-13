package com.karrotmvp.ourapt.v1.article.vote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedDto {
  @NotNull
  private List<FeedItemDto> items;
}
