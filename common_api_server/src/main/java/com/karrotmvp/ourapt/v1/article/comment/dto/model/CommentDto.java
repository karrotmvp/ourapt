package com.karrotmvp.ourapt.v1.article.comment.dto.model;

import com.karrotmvp.ourapt.v1.article.ArticleDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CommentDto extends ArticleDto {
  @NotNull
  private List<CommentDto> subComments = new ArrayList<>();
}
