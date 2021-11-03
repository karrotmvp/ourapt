package com.karrotmvp.ourapt.v1.article.comment;

import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {


  private CommentRepository commentRepository;

  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  private List<CommentDto> getCommentsByQuestionId(String questionId) {
    List<Comment> found = this.commentRepository.findByParentId(questionId);
    return null;
  }

}
