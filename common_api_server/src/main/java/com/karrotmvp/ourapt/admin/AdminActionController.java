package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.apartment.ApartmentService;
import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.question.dto.request.QuestionContentDto;
import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.article.vote.dto.request.VoteContentDto;
import com.karrotmvp.ourapt.v1.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping(value = "/admin/action")
@AllArgsConstructor
public class AdminActionController {

  private final UserService userService;
  private final ApartmentService apartmentService;
  private final QuestionService questionService;
  private final VoteService voteService;

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
    @RequestParam String voteId,
    @RequestParam String regionId,
    @RequestParam String mainText
  ) {
    final String ADMIN_USER_ID = "ADMIN";
    this.questionService.writeNewQuestion(
      new QuestionContentDto(mainText),
      ADMIN_USER_ID,
      regionId,
      voteId
    );
    rd.setUrl("/admin/questions?voteId=" + voteId);
    return rd;
  }

  @PostMapping("/new-vote.do")
  public RedirectView doCreateNewVoteAction(
    RedirectView rd,
    @RequestParam String apartmentId,
    @RequestParam String regionId,
    @ModelAttribute VoteContentDto content
  ) {
    final String ADMIN_USER_ID = "ADMIN";
    this.userService.updateCheckedInUserById(ADMIN_USER_ID, apartmentId);
    content.trimItems();
    this.voteService.writeNewVote(
      content,
      ADMIN_USER_ID,
      regionId);
    rd.setUrl("/admin/votes?perPage=20&pageNum=1");
    return rd;
  }

  // TODO: Refactor
  @PostMapping("/pin-question.do")
  public RedirectView doPinQuestionAction(
    RedirectView rd,
    @RequestParam String id
  ) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.YEAR, 100); // Permanently
    this.questionService.pin(id, cal.getTime());
    rd.setUrl("/admin/questions?perPage=20&pageNum=1");
    return rd;
  }

  @PostMapping("/unpin-question.do")
  public RedirectView doUnpinQuestionAction(
    RedirectView rd,
    @RequestParam String id
  ) {
    this.questionService.unpin(id);
    rd.setUrl("/admin/questions?perPage=20&pageNum=1");
    return rd;
  }

  @PostMapping("/delete-question.do")
  public RedirectView deleteQuestion(
    RedirectView rd,
    @RequestHeader(value = HttpHeaders.REFERER) final String referrer,
    @RequestParam String id
  ) {
    this.questionService.deleteById(id);
    rd.setUrl(referrer);
    return rd;
  }

  @PostMapping("/pin-vote.do")
  public RedirectView doPinVoteAction(
    RedirectView rd,
    @RequestParam String id
  ) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.YEAR, 100); // Permanently
    this.voteService.pin(id, cal.getTime());
    rd.setUrl("/admin/votes?perPage=20&pageNum=1");
    return rd;
  }

  @PostMapping("/unpin-vote.do")
  public RedirectView doUnpinVoteAction(
    RedirectView rd,
    @RequestParam String id
  ) {
    this.voteService.unpin(id);
    rd.setUrl("/admin/votes?perPage=20&pageNum=1");
    return rd;
  }

  @PostMapping("/delete-vote.do")
  public RedirectView deleteVote(
    RedirectView rd,
    @RequestParam String id
  ) {
    this.voteService.deleteById(id);
    rd.setUrl("/admin/votes?perPage=20&pageNum=1");
    return rd;
  }

}
