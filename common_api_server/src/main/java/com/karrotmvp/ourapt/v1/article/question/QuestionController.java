package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.question.dto.QuestionListDto;
import com.karrotmvp.ourapt.v1.article.question.dto.QuestionSubmitDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotOpenApiUserDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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
            @RequestBody @Valid QuestionSubmitDto submitted,
            @CurrentUser KarrotOpenApiUserDto userProfile
    ) {
        User foundUser = this.userRepository.findById(userProfile.getUserId())
            .orElseThrow(RegisteredUserNotFoundException::new);

        this.questionService.writeNewQuestion(submitted, foundUser);
        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}
