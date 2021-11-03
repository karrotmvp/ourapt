package com.karrotmvp.ourapt.v1.article.comment;


import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.request.WriteNewCommentDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.response.GetCommentsOfQuestionDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
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

  @GetMapping(value = "/question/{questionId}/comments")
  @ApiOperation(value = "질문에 달린 게시글 보기")
  public CommonResponseBody<GetCommentsOfQuestionDto> getCommentsOfQuestion(
    @PathVariable String questionId
  ) {
    List<CommentDto> comments = this.commentService.getCommentsByQuestionId(questionId);

    return CommonResponseBody.<GetCommentsOfQuestionDto>builder()
      .success()
      .data(new GetCommentsOfQuestionDto(comments))
      .build();
  }

  @PostMapping(value = "/question/{questionId}/comment")
  @ApiOperation(value = "새로운 댓글 작성")
  public CommonResponseBody<Void> writeNewComment(
    @RequestBody @Valid WriteNewCommentDto commentContent,
    @PathVariable String questionId,
    @CurrentUser KarrotProfile profile
  ) {
    this.commentService.writeNewComment(commentContent, questionId, profile.getId());
    return CommonResponseBody.<Void>builder()
      .success()
      .build();
  }
}
