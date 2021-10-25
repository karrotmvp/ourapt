package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.dto.QuestionFormDto;
import com.karrotmvp.ourapt.v1.article.dto.QuestionListDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotOpenApiUserProfileDto;
import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/article/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    @ApiOperation(value = "질문피드 페이징으로 조회")
    public CommonResponseBody<QuestionListDto> getQuestions(
            @RequestParam(name = "perPage") int perPage,
            @RequestParam(name = "cursor") long cursorTimestamp
    ) {
        System.out.println(cursorTimestamp);
        Date cursor = new Date(cursorTimestamp);

        return CommonResponseBody.<QuestionListDto>builder()
                .success()
                .build();
    }

    @PostMapping
    @ApiOperation(value = "새로운 질문 작성")
    public CommonResponseBody<Void> submitNewQuestion(
            @RequestBody @Valid QuestionFormDto submitted,
            @CurrentUser KarrotOpenApiUserProfileDto userProfile
    ) {
        this.questionService.writeNewQuestion(submitted, userProfile.toEntity());
        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}
