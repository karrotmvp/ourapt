package com.karrotmvp.ourapt.v1.article.comment.repository;

import com.karrotmvp.ourapt.v1.article.ArticleCustomRepository;
import com.karrotmvp.ourapt.v1.article.comment.Comment;

import java.util.List;
import java.util.Set;

public interface CommentCustomRepository extends ArticleCustomRepository<Comment, String> {
  List<Comment> findByParentId(String parentId);
  List<Comment> findByParentIdIn(Set<String> parentId);
}
