package com.karrotmvp.ourapt.v1.article.comment.repository;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository<T, ID> {
  Optional<T> findById(ID id);
  List<T> findByParentIdOrderByCreatedAtAsc(String parentId);
}
