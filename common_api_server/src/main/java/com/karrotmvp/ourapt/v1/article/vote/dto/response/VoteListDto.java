package com.karrotmvp.ourapt.v1.article.vote.dto.response;

import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoteListDto {
  private List<VoteDto> votes = new ArrayList<>();
}
