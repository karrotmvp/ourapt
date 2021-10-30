package com.karrotmvp.ourapt.v1.article.comment;


import com.karrotmvp.ourapt.v1.article.comment.dto.request.WriteNewCommentDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.response.GetCommentsOfQuestionDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "6. 댓글")
public class CommentController {

  @GetMapping(value = "/question/{questionId}/comments")
  @ApiOperation(value = "질문에 달린 게시글 보기")
  public CommonResponseBody<GetCommentsOfQuestionDto> getCommentsOfQuestion(
    @PathVariable String questionId
  ) {
    throw new UnsupportedOperationException();
//    return CommonResponseBody.<GetCommentsOfQuestionDto>builder()
//      .success()
//      .build();
  }

  @PostMapping(value = "/question/{questionId}/comment")
  @ApiOperation(value = "새로운 댓글 작성")
  public CommonResponseBody<Void> writeNewComment(
    @RequestBody @Valid WriteNewCommentDto commentContent,
    @PathVariable String questionId,
    @CurrentUser KarrotProfile profile
  ) {
    throw new UnsupportedOperationException();
//    return CommonResponseBody.<Void>builder()
//      .success().build();
  }
}
