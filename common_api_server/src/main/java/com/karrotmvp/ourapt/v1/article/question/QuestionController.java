package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.QuestionContentDto;
import com.karrotmvp.ourapt.v1.article.question.dto.response.OneQuestionDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "4-1. 투표에 대한 질문")
public class QuestionController {

  private final QuestionService questionService;

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @GetMapping(value = "/question/{questionId}")
  @ApiOperation(value = "ID로 질문 게시글 조회")
  public CommonResponseBody<OneQuestionDto> getQuestionById(
    @PathVariable(name = "questionId") String questionId
  ) {
    return CommonResponseBody.<OneQuestionDto>builder()
      .success()
      .data(new OneQuestionDto(this.questionService.getOneById(questionId)))
      .build();
  }



  @PostMapping(value = "/vote/{voteId}/question")
  @ApiOperation(value = "새로운 질문 게시글 작성")
  public CommonResponseBody<OneQuestionDto> writeNewQuestion(
    @PathVariable String voteId,
    @RequestBody @Valid QuestionContentDto questionContent,
    @RequestHeader(name = "Region-Id") String regionId,
    @CurrentUser KarrotProfile userProfile
  ) {
    QuestionDto createdQuestion = this.questionService.writeNewQuestion(questionContent, userProfile.getId(), regionId, voteId);
    return CommonResponseBody.<OneQuestionDto>builder()
      .data(new OneQuestionDto(createdQuestion))
      .success()
      .build();
  }

  @PatchMapping(value = "/question/{questionId}")
  @ApiOperation(value = "질문 게시글 수정하기")
  public CommonResponseBody<OneQuestionDto> updateQuestion(
    @PathVariable(name = "questionId") String questionId,
    @RequestBody @Valid QuestionContentDto questionContent,
    @CurrentUser KarrotProfile userProfile
  ) {
    QuestionDto updated = this.questionService.updateMainTextById(
      questionId,
      userProfile.getId(),
      questionContent.getMainText());
    return CommonResponseBody.<OneQuestionDto>builder()
      .success()
      .data(new OneQuestionDto(updated))
      .build();
  }

  @DeleteMapping(value = "/question/{questionId}")
  @ApiOperation(value = "질문 게시글 삭제하기")
  public CommonResponseBody<Void> deleteQuestion(
    @PathVariable(name = "questionId") String questionId
  ) {
    this.questionService.deleteById(questionId);
    return CommonResponseBody.<Void>builder()
      .success()
      .build();
  }
}
