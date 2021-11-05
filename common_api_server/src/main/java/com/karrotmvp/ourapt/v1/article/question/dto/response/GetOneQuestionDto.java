package com.karrotmvp.ourapt.v1.article.question.dto.response;

import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetOneQuestionDto {
  private QuestionDto question;
}
