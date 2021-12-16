package com.karrotmvp.ourapt.v1.article.comment;

import com.karrotmvp.ourapt.v1.article.Article;
import com.karrotmvp.ourapt.v1.article.ArticleBaseService;
import com.karrotmvp.ourapt.v1.article.ArticleRepository;
import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.request.WriteNewCommentDto;
import com.karrotmvp.ourapt.v1.article.comment.repository.CommentRepository;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService extends ArticleBaseService<Comment, CommentDto> {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final ArticleRepository<Article> commonArticleRepository;

  public CommentService(
    CommentRepository commentRepository,
    ModelMapper mapper,
    UserRepository userRepository,
    ArticleRepository<Article> commonArticleRepository,
    UserService userService
  ) {
    super(commentRepository, commentRepository, mapper);
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.userService = userService;
    this.commonArticleRepository = commonArticleRepository;
  }

  public CommentDto getCommentById(String commentId) {
    CommentDto found = mapper.map(this.safelyGetById(commentId), CommentDto.class);
    try {
      return joinOnSubComments(List.of(found)).get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new DataNotFoundFromDBException("Cannot found comment matched with ID: " + commentId);
    }
  }

  public List<CommentDto> getCommentsByArticleId(String articleId) {
    List<CommentDto> foundComments = this.commentRepository.findByParentId(articleId)
      .stream()
      .map(c -> mapper.map(c, CommentDto.class))
      .collect(Collectors.toList());
    return joinOnSubComments(foundComments);
  }

  public List<CommentDto> joinOnSubComments(List<CommentDto> comments) {
    Map<String, List<Comment>> subCommentsMap = this.commentRepository.findByParentIdIn(comments.stream()
      .map(CommentDto::getId)
      .collect(Collectors.toSet())
    ).stream().collect(Collectors.groupingBy(subc -> subc.getParent().getId()));
    comments.forEach(c ->
      {
        c.setSubComments(subCommentsMap.getOrDefault(
              c.getId(),
              new ArrayList<>()
            ).stream()
            .map(subc -> mapper.map(subc, CommentDto.class))
            .collect(Collectors.toList())
        );
      }
    );
    return comments;
  }

  @Transactional
  public CommentDto writeNewComment(WriteNewCommentDto content, String articleId, String writerId, String regionId) {
    User writer = this.userRepository.findById(writerId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    this.userService.assertUserIsNotBanned(writer);
    Article parent = this.commonArticleRepository.findById(articleId)
      .orElseThrow(() -> new DataNotFoundFromDBException("Cannot find the Article matched with ID: " + articleId));
    // TODO: need logic to block subcomment of subcomment
    Comment comment = mapper.map(content, Comment.class);
    comment.setWriter(writer);
    comment.setRegionWhereCreated(regionId);
    comment.setParent(parent);
    if (Comment.class.isAssignableFrom(parent.getClass())) {
      // subcomment
      comment.setRoot(((Comment) parent).getParent());
    } else {
      comment.setRoot(parent);
    }
    if (writer.getCheckedIn() == null) {
      throw new NotCheckedInUserException();
    }
    comment.setApartmentWhereCreated(parent.getApartmentWhereCreated());
    this.commentRepository.save(comment);
    return mapper.map(comment, CommentDto.class);
  }

  public void deleteCommentById(String commentId) {
    Comment toDelete = this.safelyGetById(commentId);
    toDelete.setDeletedAt(new Date());
    this.commentRepository.save(toDelete);
  }

  @Override
  protected Class<CommentDto> getClassOfDomainModel() {
    return CommentDto.class;
  }
}
