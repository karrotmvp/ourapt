package com.karrotmvp.ourapt.v1.article.question.dto.response;

import com.karrotmvp.ourapt.v1.article.dto.model.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetQuestionsDto {
    @NotNull
    List<QuestionDto> questions;
}
