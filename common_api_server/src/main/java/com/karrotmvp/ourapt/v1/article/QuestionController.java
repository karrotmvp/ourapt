package com.karrotmvp.ourapt.v1.article;

import java.util.Date;

import javax.validation.Valid;

import com.karrotmvp.ourapt.v1.article.dto.QuestionFormDto;
import com.karrotmvp.ourapt.v1.article.dto.QuestionListDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotOpenApiUserProfileDto;
import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.user.User;
import com.karrotmvp.ourapt.v1.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

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
            @RequestBody @Valid QuestionFormDto submitted,
            @CurrentUser KarrotOpenApiUserProfileDto userProfile
    ) {
        User foundUser = this.userRepository.findById(userProfile.getUserId())
            .orElseThrow(() -> new DataNotFoundFromDBException(
                "등록된 유저 없음, 우리 서비스를 사용하지 않는 당근 유저임", 
                "사용자 동의를 통한 등록을 먼저 진행해주세요."));
      
        this.questionService.writeNewQuestion(submitted, foundUser);
        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}
