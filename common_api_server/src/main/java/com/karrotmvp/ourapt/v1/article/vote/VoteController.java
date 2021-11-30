package com.karrotmvp.ourapt.v1.article.vote;

import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.request.VoteContentDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.OneVoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.PinnedVoteDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
@Api(tags = "4. 투표")
public class VoteController {

  private final VoteService voteService;
  private final QuestionService questionService;

  @GetMapping("/apartment/{apartmentId}/vote/pinned")
  @ApiOperation(value = "아파트의 핀 투표 조회")
  public CommonResponseBody<PinnedVoteDto> getRandomPinnedVoteOfApartment(
    @PathVariable String apartmentId
  ) {
    VoteDto pinned = this.voteService.getPinnedOneOfApartment(apartmentId);
    List<QuestionDto> questionsOfPinned = this.questionService.getAllQuestionsAboutVote(pinned.getId());
    return CommonResponseBody.<PinnedVoteDto>builder()
      .success()
      .data(new PinnedVoteDto(pinned, questionsOfPinned))
      .build();
  }

  @PostMapping("/vote")
  @ApiOperation(value = "새 투표 게시글 작성")
  public CommonResponseBody<OneVoteDto> writeNewVote(
    @RequestBody @Valid VoteContentDto voteContent,
    @RequestHeader(name = "Region-Id") String regionId,
    @CurrentUser KarrotProfile profile
  ) {
    VoteDto created = this.voteService.writeNewVote(voteContent, profile.getId(), regionId);
    return CommonResponseBody.<OneVoteDto>builder()
      .success()
      .data(new OneVoteDto(created))
      .build();
  }

  @PostMapping("/vote/item/{itemId}/voting")
  @ApiOperation(value = "투표 (정정) 하기")
  public CommonResponseBody<Void> submitVoting(
    @PathVariable String itemId,
    @CurrentUser KarrotProfile profile
  ) {
    this.voteService.addOrUpdateVotingToVoteItem(profile.getId(), itemId);
    return CommonResponseBody.<Void>builder()
      .success()
      .build();
  }

  @DeleteMapping("/vote/item/{itemId}/voting")
  @ApiOperation(value = "투표 취소하기")
  public CommonResponseBody<Void> cancelVoting(
    @PathVariable String itemId,
    @CurrentUser KarrotProfile profile
  ) {
    this.voteService.cancelVoting(profile.getId(), itemId);
    return CommonResponseBody.<Void>builder()
      .success()
      .build();
  }

  @GetMapping("/vote/{voteId}")
  @ApiOperation(value = "ID로 투표 게시글 조회")
  public CommonResponseBody<OneVoteDto> getVoteById(
    @PathVariable String voteId
  ) {
    throw new UnsupportedOperationException("Unsupported Yet");
  }

  @GetMapping("/apartment/{apartmentId}/votes")
  @ApiOperation(value = "아파트의 투표들 Date 커서로 페이징 조회")
  public CommonResponseBody<OneVoteDto> getVoteById(
    @RequestParam(name = "perPage") @Max(value = 10) int perPage,
    @RequestParam(name = "cursor") long cursorTimestamp,
    @PathVariable(name = "apartmentId") String apartmentId
  ) {
    throw new UnsupportedOperationException("Unsupported Yet");
  }

}
