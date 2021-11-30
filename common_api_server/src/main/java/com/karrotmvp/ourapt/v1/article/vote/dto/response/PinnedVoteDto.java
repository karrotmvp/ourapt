package com.karrotmvp.ourapt.v1.article.vote.dto.response;


import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PinnedVoteDto {

  @NotNull
  private VoteDto pinnedVote;

  @NotNull
  private List<QuestionDto> questions = new ArrayList<>();
}
