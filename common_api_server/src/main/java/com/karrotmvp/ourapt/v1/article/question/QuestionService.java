package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.QuestionContentDto;
import com.karrotmvp.ourapt.v1.article.repository.ArticleRepository;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.NoPermissionException;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final ArticleRepository<Question> questionRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    public QuestionService(ArticleRepository<Question> questionRepository, UserRepository userRepository, ModelMapper mapper, UserService userService) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public List<QuestionWithWhereCreatedDto> getQuestionsAndOriginWithOffsetCursor(int perPage, int pageNum) {
        return this.questionRepository.findByOffsetCursor(PageRequest.of(pageNum, perPage), Question.class)
          .stream().map(q -> mapper.map(q, QuestionWithWhereCreatedDto.class)).collect(Collectors.toList());
    }

    public List<QuestionDto> getQuestionsOfApartmentWithDateCursor(String apartmentId, Date cursor, int perPage) {
        return this.questionRepository
          .findFirstByApartmentIdToAndDateCursorByOrderByDesc(apartmentId, cursor, PageRequest.of(0, perPage), Question.class)
          .stream()
          .map(q -> mapper.map(q, QuestionDto.class))
          .collect(Collectors.toList());
    }

    public QuestionDto getRandomPinnedQuestionOfApartment(String apartmentId) {
        List<Question> pinnedQuestionOfApt = this.questionRepository.findByApartmentIdAndPinned(apartmentId, Question.class);
        if (pinnedQuestionOfApt.size() == 0) {
          throw new DataNotFoundFromDBException("There is no available pinned question");
        }

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return mapper.map(pinnedQuestionOfApt.get(random.nextInt(pinnedQuestionOfApt.size())), QuestionDto.class);
    }

    public QuestionWithWhereCreatedDto getQuestionsAndOriginById(String questionId) {
        Question a = this.safelyGetQuestionById(questionId);
        return mapper.map(a, QuestionWithWhereCreatedDto.class);
    }

    public QuestionDto getQuestionById(String questionId) {
        return mapper.map(this.safelyGetQuestionById(questionId), QuestionDto.class);
    }
    

    @Transactional
    public void pinQuestionUntil(String questionId, Date until) {
        Question target = safelyGetQuestionById(questionId);
        target.setPinnedUntil(until);
        this.questionRepository.save(target);
    }

    @Transactional
    public void unpinQuestion(String questionId) {
        Question target = safelyGetQuestionById(questionId);
        target.setPinnedUntil(null);
        this.questionRepository.save(target);
    }

    @Transactional
    public void deleteQuestionById(String questionId) {
        Question toDelete = this.safelyGetQuestionById(questionId);
        toDelete.setDeletedAt(new Date());
        this.questionRepository.save(toDelete);
    }

    @Transactional
    public QuestionDto updateQuestionById(String questionId, String updaterId, QuestionContentDto content)  {
        Question toUpdate = this.safelyGetQuestionById(questionId);
        if (!toUpdate.getWriter().getId().equals(updaterId)) {
            throw new NoPermissionException("You has no permission to update this");
        }
        toUpdate.setMainText(content.getMainText());
        this.questionRepository.save(toUpdate);
        return mapper.map(toUpdate, QuestionDto.class);
    }

    private Question safelyGetQuestionById(String questionId) {
        return this.questionRepository.findById(questionId, Question.class).orElseThrow(
          () -> new DataNotFoundFromDBException("There is no question match with ID: " + questionId));
    }

    public int getCountOfAllQuestions() {
        return Math.toIntExact(this.questionRepository.countByDeletedAtIsNull());
    }

    public int getCountOfTodayQuestions() {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        Date now = new Date();
        from.setTime(now);
        from.set(Calendar.HOUR, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        to.setTime(now);
        to.add(Calendar.DATE, 1);
        to.set(Calendar.HOUR, 0);
        to.set(Calendar.MINUTE, 0);
        to.set(Calendar.SECOND, 0);
        return Math.toIntExact(this.questionRepository.countByCreatedAtBetween(from.getTime(), to.getTime()));
    }

    @Transactional
    public QuestionDto writeNewQuestion(QuestionContentDto content, String writerId, String regionId) {
        User writer = this.userRepository.findById(writerId)
          .orElseThrow(RegisteredUserNotFoundException::new);
        this.userService.assertUserIsNotBanned(writer);
        Question question = mapper.map(content, Question.class);
        question.setWriter(writer);
        question.setRegionWhereCreated(regionId);
        if (writer.getCheckedIn() == null) {
            throw new NotCheckedInUserException();
        }
        question.setApartmentWhereCreated(writer.getCheckedIn());
        this.questionRepository.save(question);
        return mapper.map(question, QuestionDto.class);
    }
}
