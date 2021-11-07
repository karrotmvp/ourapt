package com.karrotmvp.ourapt.admin;

import java.util.Calendar;
import java.util.Date;

import com.karrotmvp.ourapt.v1.apartment.ApartmentService;
import com.karrotmvp.ourapt.v1.article.comment.CommentService;
import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.question.dto.request.WriteNewQuestionDto;
import com.karrotmvp.ourapt.v1.user.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/admin/action")
public class AdminActionController {

  private final UserService userService;
  private final ApartmentService apartmentService;
  private final QuestionService questionService;
  private final CommentService commentService;

  public AdminActionController(ApartmentService apartmentService, UserService userService, QuestionService questionService, CommentService commentService) {
    this.apartmentService = apartmentService;
    this.userService = userService;
    this.questionService = questionService;
    this.commentService = commentService;
  }

  @GetMapping(value = "/active-apartment.do")
  public RedirectView doTurnOnApartmentAction(
    @RequestParam String id,
    RedirectView rd
  ) {
    this.apartmentService.activateApartmentById(id);
    rd.setUrl("/admin/apartments");
    return rd;
  }

  @GetMapping(value = "/inactive-apartment.do")
  public RedirectView doTurnOffApartmentAction(
    @RequestParam String id,
    RedirectView rd
  ) {
    this.apartmentService.inactivateApartmentById(id);
    rd.setUrl("/admin/apartments");
    return rd;
  }

  @GetMapping("/ban-user.do")
  public RedirectView doBanUserAction(
    @RequestParam String id,
    RedirectView rd
  ) {
    this.userService.banUserById(id);
    rd.setUrl("/admin/users?pageNum=1&perPage=20");
    return rd;
  }

  @GetMapping("/cancel-ban-user.do")
  public RedirectView doCancelToBanUserAction(
    @RequestParam String id,
    RedirectView rd
  ) {
    this.userService.cancelBanUserById(id);
    rd.setUrl("/admin/users?pageNum=1&perPage=20");
    return rd;
  }

  @PostMapping("/new-question.do")
  public RedirectView doCreateNewQuestionAction(
    RedirectView rd,
    @RequestParam String apartmentId,
    @RequestParam String regionId,
    @RequestParam String mainText
  ) {
    final String ADMIN_USER_ID = "ADMIN";
    this.userService.updateCheckedInUserById(ADMIN_USER_ID, apartmentId);
    this.questionService.writeNewQuestion(
        new WriteNewQuestionDto(mainText, regionId)
        , ADMIN_USER_ID);
    rd.setUrl("/admin/questions?perPage=20&pageNum=1");
    return rd;
  }

  @PostMapping("/pin-question.do")
  public RedirectView doPinQuestionAction(
    RedirectView rd,
    @RequestParam String id
  ) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.YEAR, 100); // Permanently
    this.questionService.pinQuestionUntil(id, cal.getTime());
    rd.setUrl("/admin/questions?perPage=20&pageNum=1");
    return rd;
  }

  @PostMapping("/unpin-question.do")
  public RedirectView doUnpinQuestionAction(
    RedirectView rd,
    @RequestParam String id
  ) {
    this.questionService.unpinQuestion(id);
    rd.setUrl("/admin/questions?perPage=20&pageNum=1");
    return rd;
  }

}
