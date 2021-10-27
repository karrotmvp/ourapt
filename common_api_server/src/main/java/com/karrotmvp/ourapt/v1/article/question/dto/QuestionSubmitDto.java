package com.karrotmvp.ourapt.v1.article.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class QuestionSubmitDto {

    @NotNull
    private String mainText;
    @NotNull
    private String regionId;
}
