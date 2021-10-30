package com.karrotmvp.ourapt.v1.article.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionListDto {
    @NotNull
    List<QuestionSubmitDto> questions;
}
