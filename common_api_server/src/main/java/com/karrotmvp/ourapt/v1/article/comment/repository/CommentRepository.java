package com.karrotmvp.ourapt.v1.article.comment.repository;


import com.karrotmvp.ourapt.v1.article.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String>, CommentCustomRepository<Comment, String> {
  long countByDeletedAtIsNull();
}
