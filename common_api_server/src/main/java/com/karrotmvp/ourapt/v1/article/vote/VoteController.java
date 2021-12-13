package com.karrotmvp.ourapt.v1.article.vote;

import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.request.VoteContentDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.FeedDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.OneVoteDto;
import com.karrotmvp.ourapt.v1.article.vote.repository.VoteFeedService;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
@Api(tags = "4. 투표")
public class VoteController {

  private final VoteService voteService;
  private final VoteFeedService voteFeedService;
  private final QuestionService questionService;

  @GetMapping("/apartment/{apartmentId}/votes")
  @ApiOperation(value = "아파트의 진행중/종료된 투표 조회")
  public CommonResponseBody<FeedDto> getVotes(
    @RequestParam(name = "perPage") @Max(value = 10) int perPage,
    @RequestParam(name = "cursor") long cursorTimestamp,
    @PathVariable(name = "apartmentId") String apartmentId
  ) {
    return CommonResponseBody.<FeedDto>builder()
      .success()
      .data(this.voteFeedService.getFeedInApartment(apartmentId))
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
    VoteDto vote = this.voteService.getOneById(voteId);
    return CommonResponseBody.<OneVoteDto>builder()
      .success()
      .data(new OneVoteDto(vote))
      .build();
  }


}
