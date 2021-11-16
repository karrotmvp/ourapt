package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.apartment.ApartmentService;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.article.comment.CommentService;
import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

  private final QuestionService questionService;

  private final ApartmentService apartmentService;

  private final CommentService commentService;

  private final UserService userService;


  public AdminPageController(QuestionService questionService, ApartmentService apartmentService, UserService userService, CommentService commentService) {
    this.questionService = questionService;
    this.commentService =commentService;
    this.apartmentService = apartmentService;
    this.userService = userService;
  }


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
    model.addAttribute("countOfAll", this.questionService.getCountOfAllQuestions());
    model.addAttribute("countOfToday", this.questionService.getCountOfTodayQuestions());
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
}
