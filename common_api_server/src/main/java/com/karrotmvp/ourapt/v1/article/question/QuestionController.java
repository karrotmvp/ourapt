package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.QuestionContentDto;
import com.karrotmvp.ourapt.v1.article.question.dto.response.GetQuestionsDto;
import com.karrotmvp.ourapt.v1.article.question.dto.response.OneQuestionDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "4-1. 투표에 대한 질문")
public class QuestionController {

  private final QuestionService questionService;

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @GetMapping(value = "/apartment/{apartmentId}/questions/pinned")
  @ApiOperation(value = "사용자에게 보여질 핀 질문 랜덤 조회 (사용안함) ")
  @ApiIgnore
  public CommonResponseBody<OneQuestionDto> getRandomPinnedQuestionOfApartmentDeprecated(
    @PathVariable(name = "apartmentId") String apartmentId
  ) {
    return CommonResponseBody.<OneQuestionDto>builder()
      .success()
      .data(new OneQuestionDto(this.questionService.getPinnedOneOfApartment(apartmentId)))
      .build();
  }

  @GetMapping(value = "/apartment/{apartmentId}/question/pinned")
  @ApiOperation(value = "사용자에게 보여질 핀 질문 랜덤 조회 (사용안함) ")
  @ApiIgnore
  public CommonResponseBody<OneQuestionDto> getRandomPinnedQuestionOfApartment(
    @PathVariable(name = "apartmentId") String apartmentId
  ) {
    return CommonResponseBody.<OneQuestionDto>builder()
      .success()
      .data(new OneQuestionDto(this.questionService.getPinnedOneOfApartment(apartmentId)))
      .build();
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

  @GetMapping(value = "/apartment/{apartmentId}/questions")
  @ApiOperation(value = "질문 게시글 목록 Date 커서 기반 페이징으로 조회 (사용안함)")
  @ApiIgnore
  public CommonResponseBody<GetQuestionsDto> getQuestions(
    @RequestParam(name = "perPage") @Max(value = 10) int perPage,
    @RequestParam(name = "cursor") long cursorTimestamp,
    @PathVariable(name = "apartmentId") String apartmentId
  ) {
    List<QuestionDto> questions = this.questionService.getPageOfApartmentWithDateCursor(
      apartmentId,
      new Date(cursorTimestamp),
      perPage);
    return CommonResponseBody.<GetQuestionsDto>builder()
      .success()
      .data(new GetQuestionsDto(questions))
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
