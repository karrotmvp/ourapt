package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.dto.QuestionDto;
import com.karrotmvp.ourapt.v1.article.dto.QuestionFormDto;
import com.karrotmvp.ourapt.v1.article.entity.Question;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.user.User;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.dto.KarrotOApiUserDto;
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
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void writeNewQuestion(QuestionFormDto questionInfo, User writer) {
        Question question = modelMapper.map(questionInfo, Question.class);
        question.setWriter(writer);
        this.questionRepository.save(question);
    }

    public long getCountOfAllQuestions() {
        return this.questionRepository.count();
    }

    public long getCountOfNotEmptyQuestions() {
        return this.questionRepository.countByMainTextNot("");
    }

    public List<QuestionDto> getQuestionsWithCursorPaging(Date cursor, int perPage) {
        List<QuestionDto> questions = questionRepository.findByDateCursorWithPaging(cursor, PageRequest.of(0, perPage))
                .stream()
                .map(q -> modelMapper.map(q, QuestionDto.class))
                .peek(qd -> qd.setRegionName(Static.apartmentDict.get(qd.getRegionName())))
                .collect(Collectors.toList());
        List<KarrotOApiUserDto> writers = this.userService.getKarrotUserProfilesByIds(
                questions.stream().map(q -> q.getWriter().getId()).collect(Collectors.toSet())
        );
        return Utils.leftOuterHashJoin(
                questions,
                writers,
                (q) -> q.getWriter().getId(),
                KarrotOApiUserDto::getId,
                QuestionDto::setWriter
        );
    }

    public List<QuestionDto> getQuestionsWithOffsetPaging(int perPage, int pageNum) {
        List<QuestionDto> questions = questionRepository.findByOrderByCreatedAtDesc(PageRequest.of(pageNum, perPage))
                .stream()
                .map(q -> modelMapper.map(q, QuestionDto.class))
                .peek(qd -> qd.setRegionName(Static.apartmentDict.get(qd.getRegionId())))
                .collect(Collectors.toList());
        List<KarrotOApiUserDto> writers = this.userService.getKarrotUserProfilesByIds(
                questions.stream().map(q -> q.getWriter().getId()).collect(Collectors.toSet())
        );
        return Utils.leftOuterHashJoin(
                questions,
                writers,
                (q) -> q.getWriter().getId(),
                KarrotOApiUserDto::getId,
                QuestionDto::setWriter
        );
    }
}
