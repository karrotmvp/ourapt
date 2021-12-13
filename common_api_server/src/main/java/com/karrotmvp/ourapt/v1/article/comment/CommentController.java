package com.karrotmvp.ourapt.v1.article.comment;


import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.request.WriteNewCommentDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.response.CommentListDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.response.OneCommentDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "5. 댓글")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping(value = "/article/{articleId}/comments")
  @ApiOperation(value = "게시글에 달린 게시글 보기")
  public CommonResponseBody<CommentListDto> getCommentsOfQuestion(
    @PathVariable String articleId
  ) {
    List<CommentDto> comments = this.commentService.getCommentsByArticleId(articleId);
    return CommonResponseBody.<CommentListDto>builder()
      .success()
      .data(new CommentListDto(comments))
      .build();
  }

  @PostMapping(value = "/article/{articleId}/comment")
  @ApiOperation(value = "새로운 댓글 작성")
  public CommonResponseBody<OneCommentDto> writeNewComment(
    @RequestBody @Valid WriteNewCommentDto commentContent,
    @PathVariable String articleId,
    @CurrentUser KarrotProfile profile,
    @RequestHeader(name = "Region-Id") String regionId
  ) {
    CommentDto created = this.commentService.writeNewComment(commentContent, articleId, profile.getId(), regionId);
    return CommonResponseBody.<OneCommentDto>builder()
      .data(new OneCommentDto(created))
      .success()
      .build();
  }

  @DeleteMapping(value = "/comment/{commentId}")
  @ApiOperation(value = "댓글 삭제하기")
  public CommonResponseBody<Void> deleteComment(
    @PathVariable String commentId
  ) {
    this.commentService.deleteCommentById(commentId);
    return CommonResponseBody.<Void>builder()
      .success()
      .build();
  }
}
