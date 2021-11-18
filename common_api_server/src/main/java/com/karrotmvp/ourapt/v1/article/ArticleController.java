package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.dto.response.OneArticleDto;
import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
@Api(tags = "4. Article 피드")
public class ArticleController {

  private final QuestionService questionService;

  private final VoteService voteService;

  public ArticleController(QuestionService questionService, VoteService voteService) {
    this.questionService = questionService;
    this.voteService = voteService;
  }

  @GetMapping(value = "/apartment/{apartmentId}/articles/pinned")
  @ApiOperation(value = "사용자에게 보여질 pinned_article 랜덤 조회")
  public CommonResponseBody<OneArticleDto> getRandomPinnedArticleOfApartment(
    @PathVariable(name = "apartmentId") String apartmentId
  ) {
    OneArticleDto resData = new OneArticleDto();
    if (Utils.getRandomInt(2) % 2 == 0) { // 0%2 or 1%2
      resData.setQuestion(this.questionService.getRandomPinnedOfApartment(apartmentId));
    } else {
      resData.setVote(this.voteService.getRandomPinnedOfApartment(apartmentId));
    }

    return CommonResponseBody.<OneArticleDto>builder()
      .success()
      .data(resData)
      .build();
  }
}
