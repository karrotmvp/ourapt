package com.karrotmvp.ourapt.v1.comment.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class WriteNewCommentDto {

    @NotNull
    @NotEmpty
    private String mainText;
}