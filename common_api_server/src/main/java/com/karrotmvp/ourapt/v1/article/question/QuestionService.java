package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.ArticleBaseService;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.QuestionContentDto;
import com.karrotmvp.ourapt.v1.article.question.repository.QuestionRepository;
import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService extends ArticleBaseService<Question, QuestionDto> {

  private final UserRepository userRepository;
  private final QuestionRepository questionRepository;
  private final UserService userService;
  private final VoteService voteService;

  public QuestionService(
    QuestionRepository questionRepository,
    UserRepository userRepository,
    ModelMapper mapper,
    UserService userService,
    VoteService voteService
  ) {
    super(questionRepository, questionRepository, mapper);
    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
    this.userService = userService;
    this.voteService = voteService;
  }

  public QuestionWithWhereCreatedDto getQuestionsAndOriginById(String questionId) {
    Question found = this.safelyGetById(questionId);
    return mapper.map(found, QuestionWithWhereCreatedDto.class);
  }


  @Transactional(rollbackFor = RuntimeException.class)
  public QuestionDto writeNewQuestion(QuestionContentDto content, String writerId, String regionId) {
    User writer = this.userRepository.findById(writerId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    this.userService.assertUserIsNotBanned(writer);
    if (writer.getCheckedIn() == null) {
      throw new NotCheckedInUserException();
    }
    Question question = mapper.map(content, Question.class);
    question.setWriter(writer);
    question.setRegionWhereCreated(regionId);
    question.setApartmentWhereCreated(writer.getCheckedIn());
    Question saved = this.articleRepository.save(question);
    return mapper.map(saved, QuestionDto.class);
  }

  @Override
  protected Class<QuestionDto> getClassOfDomainModel() {
    return QuestionDto.class;
  }

}
