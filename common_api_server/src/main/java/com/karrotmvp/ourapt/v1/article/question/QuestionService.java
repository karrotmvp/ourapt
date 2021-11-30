package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.ArticleBaseService;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.QuestionContentDto;
import com.karrotmvp.ourapt.v1.article.question.repository.QuestionRepository;
import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService extends ArticleBaseService<Question, QuestionDto> {

  private final UserRepository userRepository;
  private final QuestionRepository questionRepository;
  private final UserService userService;
  private final VoteService voteService;

  public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, ModelMapper mapper, UserService userService, VoteService voteService) {
    super(questionRepository, questionRepository, mapper);
    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
    this.userService = userService;
    this.voteService = voteService;
  }

  public List<QuestionWithWhereCreatedDto> getQuestionsAndOriginWithOffsetCursor(int perPage, int pageNum) {
    return this.getPageAndOriginWithOffsetCursor(perPage, pageNum, QuestionWithWhereCreatedDto.class);
  }

  public QuestionWithWhereCreatedDto getQuestionsAndOriginById(String questionId) {
    Question found = this.safelyGetById(questionId);
    return mapper.map(found, QuestionWithWhereCreatedDto.class);
  }

  public List<QuestionDto> getAllQuestionsAboutVote(String voteId) {
    List<Question> questionsAboutVote = this.questionRepository.findByAboutId(voteId);
    return questionsAboutVote.stream()
      .map((q) -> mapper.map(q, QuestionDto.class))
      .collect(Collectors.toList());
  }

  @Transactional(rollbackFor = RuntimeException.class)
  public QuestionDto writeNewQuestion(QuestionContentDto content, String writerId, String regionId, String parentVoteId) {
    User writer = this.userRepository.findById(writerId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    this.userService.assertUserIsNotBanned(writer);
    if (writer.getCheckedIn() == null) {
      throw new NotCheckedInUserException();
    }
    Question question = mapper.map(content, Question.class);
    Vote subject = this.voteService.safelyGetById(parentVoteId);
    question.setWriter(writer);
    question.setAbout(subject);
    question.setRegionWhereCreated(regionId);
    question.setApartmentWhereCreated(subject.getApartmentWhereCreated());
    Question saved = this.articleRepository.save(question);
    return mapper.map(saved, QuestionDto.class);
  }

  @Override
  protected Class<QuestionDto> getClassOfDomainModel() {
    return QuestionDto.class;
  }

}
