package com.karrotmvp.ourapt.v1.article.dto.response;

import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OneArticleDto {
  private QuestionDto question;
  private VoteDto vote;
}
