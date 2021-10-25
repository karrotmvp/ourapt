package com.karrotmvp.ourapt.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    @GetMapping("")
    public String renderIndex() {
        return "index";
    }

    @GetMapping("/questions")
    public String renderQuestionsPage() {
        return "questions";
    }

}
