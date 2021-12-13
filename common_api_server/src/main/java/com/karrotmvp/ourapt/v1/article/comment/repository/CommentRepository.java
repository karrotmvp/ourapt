package com.karrotmvp.ourapt.v1.article.comment.repository;


import com.karrotmvp.ourapt.v1.article.ArticleRepository;
import com.karrotmvp.ourapt.v1.article.comment.Comment;

public interface CommentRepository extends ArticleRepository<Comment>, CommentCustomRepository {
}
