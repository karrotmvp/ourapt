package com.karrotmvp.ourapt.v1.article.comment.dto;


import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CommentSubmitDto {

    @NotNull
    @NotEmpty
    private String parentId;

    @NotNull
    @NotEmpty
    private String mainText;
}
