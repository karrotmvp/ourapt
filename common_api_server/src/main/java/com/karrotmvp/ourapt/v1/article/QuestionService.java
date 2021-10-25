package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.dto.QuestionDto;
import com.karrotmvp.ourapt.v1.article.entity.Question;
import com.karrotmvp.ourapt.v1.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void writeNewQuestion(QuestionDto questionInfo, User writer) {
        Question question = modelMapper.map(questionInfo, Question.class);
        question.setWriter(writer);
        this.questionRepository.save(question);
    }

    public List<QuestionDto> getQuestionsWithCursorPaging(Date cursor, int perPage) {
        List<Question> questions = questionRepository.findByDateCursorWithPaging(cursor, PageRequest.of(0, perPage));
        return questions.stream().map(q -> modelMapper.map(q, QuestionDto.class)).collect(Collectors.toList());
    }

    public List<QuestionDto> getQuestionsWithOffsetPaging(int perPage, int pageNum) {
        List<Question> questions = questionRepository.findAll(PageRequest.of(pageNum, perPage)).toList();
        return questions.stream().map(q -> modelMapper.map(q, QuestionDto.class)).collect(Collectors.toList());
    }
}
