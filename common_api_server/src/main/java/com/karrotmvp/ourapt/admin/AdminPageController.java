package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.apartment.ApartmentService;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.preopen.PreopenRepository;
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

  private final UserService userService;

  private final PreopenRepository preopenRepository;

  public AdminPageController(QuestionService questionService, PreopenRepository preopenRepository, ApartmentService apartmentService, UserService userService) {
    this.questionService = questionService;
    this.preopenRepository = preopenRepository;
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
    List<UserDto> allUsers = this.userService.getUsersWithPagination(perPage, pageNum - 1);
    model.addAttribute("users", allUsers);
    model.addAttribute("countOfAll", allUsers.size());
    model.addAttribute("countOfTheBanned", allUsers
      .stream().filter(u -> u.getBannedAt() != null).count());
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("perPage", perPage);
    model.addAttribute("isLastPage", allUsers.size() < perPage);

    return "pages/users";
  }
}
