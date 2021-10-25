package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.article.QuestionService;
import com.karrotmvp.ourapt.v1.article.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public String renderIndex() {
        return "index";
    }

    @GetMapping("/questions")
    public String renderQuestionsPage(
            Model model,
            @RequestParam(name = "perPage") int perPage,
            @RequestParam(name = "pageNum") int pageNum
    ) {
        if (pageNum < 1) {
            throw new RuntimeException("pageNum must larger than 0");
        }
        List<QuestionDto> questions = new ArrayList<>(this.questionService.getQuestionsWithOffsetPaging(perPage, pageNum - 1));
        long countOfAll = this.questionService.getCountOfAllQuestions();
        long countOfTextNotEmpty = this.questionService.getCountOfNotEmptyQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("perPage", perPage);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("isLastPage", questions.size() < perPage);
        model.addAttribute("countOfAll", countOfAll);
        model.addAttribute("countOfTextNotEmpty", countOfTextNotEmpty);
        return "questions";
    }

}
