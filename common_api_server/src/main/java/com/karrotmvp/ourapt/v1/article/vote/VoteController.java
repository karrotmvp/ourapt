package com.karrotmvp.ourapt.v1.article.vote;

import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.request.VoteContentDto;
import com.karrotmvp.ourapt.v1.article.vote.dto.response.OneVoteDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
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

  @PostMapping("/vote")
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
}
