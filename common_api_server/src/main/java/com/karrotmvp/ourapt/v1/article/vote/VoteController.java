package com.karrotmvp.ourapt.v1.article.vote;

import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.request.VoteContentDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.OneVoteDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "투표")
public class VoteController {

  private final VoteService voteService;

  public VoteController(VoteService voteService) {
    this.voteService = voteService;
  }


  @GetMapping("/apartment/{apartmentId}/vote/pinned")
  @ApiOperation(value = "사용자에게 보여질 핀 질문 랜덤 조회")
  public CommonResponseBody<OneVoteDto> getRandomPinnedVoteOfApartment(
    @PathVariable String apartmentId
  ) {
    return CommonResponseBody.<OneVoteDto>builder()
      .success()
      .data(new OneVoteDto(this.voteService.getRandomPinnedOfApartment(apartmentId)))
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

}
