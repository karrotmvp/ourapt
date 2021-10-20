package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.dto.QuestionDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotUserProfile;
import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/article/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    @ApiOperation(value = "새로운 질문 작성")
    public CommonResponseBody<Void> submitNewQuestion(
            @RequestBody @Valid QuestionDto submitted,
            @CurrentUser KarrotUserProfile userProfile
    ) {
        this.questionService.writeNewQuestion(submitted, userProfile.toEntity());
        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}
