package com.karrotmvp.ourapt.v1.article.comment;


import com.karrotmvp.ourapt.v1.article.comment.dto.CommentSubmitDto;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.article.question.QuestionRepository;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotOpenApiUserDto;
import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "")
    public CommonResponseBody<Void> submitComment(
            @RequestBody CommentSubmitDto submitted,
            @CurrentUser KarrotOpenApiUserDto user
            ) {
        Comment newComment = modelMapper.map(submitted, Comment.class);

        User foundUser = userRepository.findById(user.getUserId())
                .orElseThrow(RegisteredUserNotFoundException::new);
        newComment.setWriter(foundUser);

        Question parentQuestion = this.questionRepository.findById(submitted.getParentId())
                .orElseThrow(() -> new DataNotFoundFromDBException("잘못된 게시글 ID, 게시글이 데이터 베이스에 없음", "이미 삭제된 게시글 이에요."));
        newComment.setParent(parentQuestion);

        commentRepository.save(newComment);
        return CommonResponseBody.<Void>builder()
                .success().build();
    }
}
