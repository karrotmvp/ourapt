package com.karrotmvp.ourapt.v1.article.question;

import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.request.WriteNewQuestionDto;
import com.karrotmvp.ourapt.v1.article.question.exposure.Exposure;
import com.karrotmvp.ourapt.v1.article.question.exposure.ExposureRepository;
import com.karrotmvp.ourapt.v1.article.question.repository.QuestionRepository;
import com.karrotmvp.ourapt.v1.common.exception.application.NotCheckedInUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ExposureRepository exposureRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository, ModelMapper mapper, ExposureRepository exposureRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.exposureRepository = exposureRepository;
    }

    @Transactional
    public void writeNewQuestion(WriteNewQuestionDto content, String writerId) {
        User writer = this.userRepository.findById(writerId)
          .orElseThrow(RegisteredUserNotFoundException::new);
        Question question = mapper.map(content, Question.class);
        question.setWriter(writer);
        question.setRegionWhereCreated(content.getRegionId());
        if (writer.getCheckedIn() == null) {
            throw new NotCheckedInUserException();
        }
        question.setApartmentWhereCreated(writer.getCheckedIn());
        this.questionRepository.save(question);
        this.exposureRepository.save(new Exposure(question, writer.getCheckedIn()));
    }

    public List<QuestionDto> getQuestionsExposedToApartment(String apartmentId, Date cursor, int perPage) {
        return this.questionRepository
          .findFirstByExposedToAndDateCursorByOrderByDesc(apartmentId, cursor, PageRequest.of(0, perPage))
          .stream()
          .map(q -> mapper.map(q, QuestionDto.class))
          .collect(Collectors.toList());
    }

//    public long getCountOfAllQuestions() {}

    public long getCountOfNotEmptyQuestions() {
        return this.questionRepository.countByMainTextNot("");
    }

//    public List<QuestionDto> getQuestionsWithOffsetPaging(int perPage, int pageNum) {
//        List<QuestionDto> questions = questionRepository.findByOrderByCreatedAtDesc(PageRequest.of(pageNum, perPage))
//                .stream()
//                .map(q -> modelMapper.map(q, QuestionDto.class))
//                .peek(qv -> qv.setRegionName(Static.regionDict.get(qv.getRegionId())))
//                .collect(Collectors.toList());
//        List<KarrotOApiUserDto> writers = this.userService.getKarrotUserProfilesByIds(
//                questions.stream().map(q -> q.getWriter().getId()).collect(Collectors.toSet())
//        );
//        return Utils.leftOuterHashJoin(
//                questions,
//                writers,
//                (q) -> q.getWriter().getId(),
//                KarrotOApiUserDto::getId,
//                QuestionDto::setWriter
//        ).stream()
//                .sorted((qv1, qv2) -> (int) (qv2.getCreatedAt().getTime() - qv1.getCreatedAt().getTime()))
//                .collect(Collectors.toList());
//    }
}
