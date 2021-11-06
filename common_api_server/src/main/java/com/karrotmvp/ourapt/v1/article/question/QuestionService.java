package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.WriteNewQuestionDto;
import com.karrotmvp.ourapt.v1.article.question.repository.QuestionRepository;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, ModelMapper mapper, UserService userService) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public List<QuestionWithWhereCreatedDto> getQuestionsAndOriginWithOffsetCursor(int perPage, int pageNum) {
        return this.questionRepository.findByOffsetCursor(PageRequest.of(pageNum, perPage))
          .stream().map(q -> mapper.map(q, QuestionWithWhereCreatedDto.class)).collect(Collectors.toList());
    }

    public List<QuestionDto> getQuestionsOfApartmentWithDateCursor(String apartmentId, Date cursor, int perPage) {
        return this.questionRepository
          .findFirstByApartmentIdToAndDateCursorByOrderByDesc(apartmentId, cursor, PageRequest.of(0, perPage))
          .stream()
          .map(q -> mapper.map(q, QuestionDto.class))
          .collect(Collectors.toList());
    }

    public QuestionDto getRandomPinnedQuestionOfApartment(String apartmentId) {
        List<Question> pinnedQuestionOfApt = this.questionRepository.findByApartmentIdAndPinned(apartmentId);
        if (pinnedQuestionOfApt.size() == 0) {
          throw new DataNotFoundFromDBException("There is no available pinned question");
        }

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return mapper.map(pinnedQuestionOfApt.get(random.nextInt(pinnedQuestionOfApt.size())), QuestionDto.class);
    }

    public QuestionDto getQuestionById(String questionId) {
        return mapper.map(this.safelyGetQuestionById(questionId), QuestionDto.class);
    }

    private Question safelyGetQuestionById(String questionId) {
        return this.questionRepository.findById(questionId).orElseThrow(
          () -> new DataNotFoundFromDBException("There is no question match with ID: " + questionId));
    }

    @Transactional
    public QuestionDto writeNewQuestion(WriteNewQuestionDto content, String writerId) {
        User writer = this.userRepository.findById(writerId)
          .orElseThrow(RegisteredUserNotFoundException::new);
        this.userService.assertUserIsNotBanned(writer);
        Question question = mapper.map(content, Question.class);
        question.setWriter(writer);
        question.setRegionWhereCreated(content.getRegionId());
        if (writer.getCheckedIn() == null) {
            throw new NotCheckedInUserException();
        }
        question.setApartmentWhereCreated(writer.getCheckedIn());
        this.questionRepository.save(question);
        return mapper.map(question, QuestionDto.class);
    }

}
