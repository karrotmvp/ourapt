package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.dto.QuestionDto;
import com.karrotmvp.ourapt.v1.article.entity.Question;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotUserProfile;
import com.karrotmvp.ourapt.v1.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void writeNewQuestion(QuestionDto questionInfo, User writer) {
        Question question = modelMapper.map(questionInfo, Question.class);
        question.setUser(writer);
        this.questionRepository.save(question);
    }

}
