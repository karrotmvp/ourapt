package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.apartment.ApartmentService;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.article.comment.CommentService;
import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
@AllArgsConstructor
public class AdminPageController {

  private final QuestionService questionService;
  private final VoteService voteService;
  private final ApartmentService apartmentService;
  private final CommentService commentService;
  private final UserService userService;
  private final StatisticService statisticService;

  @GetMapping("")
  public String renderIndex() {
    return "pages/index";
  }

  @GetMapping("/apartments")
  public String renderApartmentList(
    Model model
  ) {
    List<ApartmentDto> allApartments = this.apartmentService.getAllApartments();
    int countOfTheActive = (int) allApartments.stream().filter(ApartmentDto::isActive).count();
    model.addAttribute("apartments", allApartments
      .stream()
      .sorted((apt1, apt2) -> String.CASE_INSENSITIVE_ORDER.compare(apt1.getName(), apt2.getName()))
      .collect(Collectors.toList()));

    model.addAttribute("countOfAll", allApartments.size());
    model.addAttribute("countOfTheActive", countOfTheActive);
    return "pages/apartments";
  }

  @GetMapping("/users")
  public String renderUserList(
    Model model,
    @RequestParam int perPage,
    @RequestParam int pageNum
  ) {
    List<UserDto> users = this.userService.getUsersWithPagination(perPage, pageNum - 1);
    model.addAttribute("users", users);
    model.addAttribute("countOfAll", this.userService.getCountOfAllUsers());
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("perPage", perPage);
    model.addAttribute("isLastPage", users.size() < perPage);

    return "pages/users";
  }

  @GetMapping("/votes")
  public String renderVoteList(
    Model model,
    @RequestParam int perPage,
    @RequestParam int pageNum
  ){
    List<VoteWithWhereCreatedDto> votes = this.voteService.getVotesAndOriginWithOffsetCursor(perPage, pageNum - 1);
    model.addAttribute("votes", votes);
    model.addAttribute("countOfAll", this.voteService.getCountOfAll());
    model.addAttribute("countOfToday", this.voteService.getCountInToday());
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("perPage", perPage);
    model.addAttribute("isLastPage", votes.size() < perPage);
    List<ApartmentDto> apartments = this.apartmentService.getAvailableApartments();
    model.addAttribute("apartments", apartments);

    return "pages/votes";
  }

  @GetMapping("/comments")
  public String renderComments(
    Model model,
    @RequestParam(name = "article_id") String articleId
  ) {
    model.addAttribute("comments", this.commentService.getCommentsByArticleId(articleId));
    model.addAttribute("articleId", articleId);
    return "pages/comments";
  }

  @GetMapping("/statistic")
  public String renderStatistic(
    Model model,
    @RequestParam(name = "funnel_start_date", required = false) Long funnelStartDate,
    @RequestParam(name = "funnel_end_date", required = false) Long funnelEndDate,
    @RequestParam(name = "cohort_date", required = false) Long cohortDate
  ) {
    model.addAttribute("period", Arrays.stream(statisticService.getLast7DateFormats(new Date()))
      .map(date -> date.substring(5).replaceFirst("-", "/")).toArray(String[]::new));
    model.addAttribute("dau", statisticService.getLast7DaysDailyActiveUsers(new Date()));
    model.addAttribute("signUpUser", statisticService.getLast7DaysDailySigningUpUsers(new Date()));
    model.addAttribute("funnel_label", new String[] {
      "(아파트목록보기)", "(아파트가없나요? 제출)", "(체크인)", "메인피드", "게시글 상세(댓글보기)", "게시글 작성", "댓글 작성", "투표(정정)하기", "투표취소"
    });
    Date now = new Date();
    model.addAttribute("funnel_data", this.statisticService.getFunnelBetween(
      funnelStartDate != null ? new Date(funnelStartDate) : now,
      funnelEndDate != null ? new Date(funnelEndDate) : now)
    );

    model.addAttribute("cohort", statisticService.getCohortRetention(
      cohortDate != null ? new Date(cohortDate) : now)
    );

    return "pages/statistic";
  }
}
