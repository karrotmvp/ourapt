package com.karrotmvp.ourapt.v1.article.comment;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {

  List<Comment> findByParentId(String articleId);
}
