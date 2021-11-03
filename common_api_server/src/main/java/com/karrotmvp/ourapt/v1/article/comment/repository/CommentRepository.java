package com.karrotmvp.ourapt.v1.article.comment.repository;


import com.karrotmvp.ourapt.v1.article.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {

  List<Comment> findByParentId(String articleId);
}
