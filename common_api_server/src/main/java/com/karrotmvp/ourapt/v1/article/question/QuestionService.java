package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.ArticleService;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.QuestionContentDto;
import com.karrotmvp.ourapt.v1.article.question.repository.QuestionRepository;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService extends ArticleService<Question, QuestionDto> {

  private final UserRepository userRepository;
  private final UserService userService;

  public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, ModelMapper mapper, UserService userService) {
    super(questionRepository, questionRepository, mapper);
    this.userRepository = userRepository;
    this.userService = userService;
  }

  public List<QuestionWithWhereCreatedDto> getQuestionsAndOriginWithOffsetCursor(int perPage, int pageNum) {
    return this.articleCustomRepository.findByOffsetCursor(PageRequest.of(pageNum, perPage))
      .stream().map(q -> mapper.map(q, QuestionWithWhereCreatedDto.class)).collect(Collectors.toList());
  }

  public QuestionWithWhereCreatedDto getQuestionsAndOriginById(String questionId) {
    Question a = this.safelyGetById(questionId);
    return mapper.map(a, QuestionWithWhereCreatedDto.class);
  }

  @Transactional(rollbackFor = RuntimeException.class)
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
    Question saved = this.articleRepository.save(question);
    return mapper.map(saved, QuestionDto.class);
  }

  @Override
  protected Class<QuestionDto> getClassOfDomainModel() {
    return QuestionDto.class;
  }

}
