package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.apartment.ApartmentService;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.article.vote.dto.model.VoteWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.comment.CommentService;
import com.karrotmvp.ourapt.v1.comment.dto.model.CommentDto;
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

  @GetMapping("/questions")
  public String renderQuestionList(
    Model model,
    @RequestParam int perPage,
    @RequestParam int pageNum
  ) {
    List<QuestionWithWhereCreatedDto> questions = this.questionService.getQuestionsAndOriginWithOffsetCursor(perPage, pageNum - 1);
    model.addAttribute("questions", questions);
    model.addAttribute("countOfAll", this.questionService.getCountOfAll());
    model.addAttribute("countOfToday", this.questionService.getCountInToday());
    model.addAttribute("countOfAllComments", this.commentService.getCountOfAllComments());
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("perPage", perPage);
    model.addAttribute("isLastPage", questions.size() < perPage);
    List<ApartmentDto> apartments = this.apartmentService.getAvailableApartments();
    model.addAttribute("apartments", apartments);
    return "pages/questions";
  }

  @GetMapping("/question/detail")
  public String renderQuestionDetail(
    Model model,
    @RequestParam String id
  ) {
    QuestionWithWhereCreatedDto question = this.questionService.getQuestionsAndOriginById(id);
    List<CommentDto> comments = this.commentService.getCommentsByQuestionId(id);
    model.addAttribute("question", question);
    model.addAttribute("comments", comments);
    return "pages/question-detail";
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

  @GetMapping("/statistic")
  public String renderStatistic(
    Model model,
    @RequestParam(name = "funnel_date", required = false) Long funnelDate
  ) {
    model.addAttribute("period", Arrays.stream(statisticService.getLast7DateFormats(new Date()))
      .map(date -> date.substring(5).replaceFirst("-", "/")).toArray(String[]::new));
    model.addAttribute("dau", statisticService.getLast7DaysDailyActiveUsers(new Date()));
    model.addAttribute("signUpUser", statisticService.getLast7DaysDailySigningUpUsers(new Date()));
    model.addAttribute("feedUser", statisticService.getLast7DaysSeeingFeedUsers(new Date()));
    model.addAttribute("funnel_label", new String[] {
      "(아파트진입)", "(체크인)", "메인피드", "게시글 상세(댓글보기)", "게시글 작성", "댓글 작성", "투표(정정)하기", "투표취소"
    });
    model.addAttribute("funnel_data", this.statisticService.getFunnelOfDaily(funnelDate != null ? new Date(funnelDate) : new Date()));

    return "pages/statistic";
  }
}
