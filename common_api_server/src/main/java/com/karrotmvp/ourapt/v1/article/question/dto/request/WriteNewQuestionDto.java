package com.karrotmvp.ourapt.v1.article.question.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WriteNewQuestionDto {

    @NotNull
    @NotEmpty
    private String mainText;

    @NotNull
    @NotEmpty
    private String regionId;
}
