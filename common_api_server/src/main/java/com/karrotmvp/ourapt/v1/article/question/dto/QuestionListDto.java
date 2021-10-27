package com.karrotmvp.ourapt.v1.article.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionListDto {
    List<QuestionSubmitDto> questions;
}
