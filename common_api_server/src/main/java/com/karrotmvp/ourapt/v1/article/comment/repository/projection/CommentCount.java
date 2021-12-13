package com.karrotmvp.ourapt.v1.article.comment.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentCount {

  private String parentId;
  private long commentCount;

}
