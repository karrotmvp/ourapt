package com.karrotmvp.ourapt.v1.article.comment.repository;

import java.util.List;

public interface CommentCustomRepository<T, ID> {
  List<T> findByParentIdOrderByCreatedAtAsc(String parentId);
}
