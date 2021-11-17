package com.karrotmvp.ourapt.v1.comment;

import com.karrotmvp.ourapt.v1.article.Article;
import com.karrotmvp.ourapt.v1.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.comment.dto.request.WriteNewCommentDto;
import com.karrotmvp.ourapt.v1.comment.repository.CommentRepository;
import com.karrotmvp.ourapt.v1.article.question.repository.QuestionRepository;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

  private final QuestionRepository questionRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ModelMapper mapper;
  private final UserService userService;

  public CommentService(QuestionRepository questionRepository, CommentRepository commentRepository, UserRepository userRepository, ModelMapper mapper, UserService userService) {
    this.questionRepository = questionRepository;
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.mapper = mapper;
    this.userService = userService;
  }

  public List<CommentDto> getCommentsByQuestionId(String questionId) {
    List<Comment> foundComments = this.commentRepository.findByParentIdOrderByCreatedAtAsc(questionId);
    return foundComments.stream()
      .map(c -> mapper.map(c, CommentDto.class))
      .collect(Collectors.toList());
  }

  @Transactional
  public CommentDto writeNewComment(WriteNewCommentDto content, String questionId,  String writerId, String regionId) {
    User writer = this.userRepository.findById(writerId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    this.userService.assertUserIsNotBanned(writer);
    Article parent = this.questionRepository.findById(questionId)
      .orElseThrow(() -> new DataNotFoundFromDBException("해당하는 Article이 없습니다."));
    Comment comment = mapper.map(content, Comment.class);
    comment.setWriter(writer);
    comment.setRegionWhereCreated(regionId);
    comment.setParent(parent);
    if (writer.getCheckedIn() == null) {
      throw new NotCheckedInUserException();
    }
    comment.setApartmentWhereCreated(writer.getCheckedIn());
    this.commentRepository.save(comment);
    return mapper.map(comment, CommentDto.class);
  }

  public void deleteCommentById(String commentId) {
    Comment toDelete = this.safelyGetCommentById(commentId);
    toDelete.setDeletedAt(new Date());
    this.commentRepository.save(toDelete);
  }

  public int getCountOfAllComments() {
    return Math.toIntExact(this.commentRepository.countByDeletedAtIsNull());
  }

  private Comment safelyGetCommentById(String commentId) {
    return this.commentRepository.findById(commentId).orElseThrow(
      () -> new DataNotFoundFromDBException("Cannot found matched comment with id: " + commentId));
  }
}
