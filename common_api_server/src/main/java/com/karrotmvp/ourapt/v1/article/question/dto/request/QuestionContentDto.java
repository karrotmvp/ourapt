package com.karrotmvp.ourapt.v1.article.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionContentDto {

    @NotNull
    @NotEmpty
    private String mainText;
}
