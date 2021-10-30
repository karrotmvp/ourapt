package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.adminquestion.dto.model.AdminQuestionAnswerDto;
import com.karrotmvp.ourapt.v1.adminquestion.dto.model.AdminQuestionDto;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestion;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestionAnswer;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.model.RegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.article.comment.Comment;
import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.user.dto.UserDto;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    // Region
    modelMapper.typeMap(Region.class, RegionDto.class).addMappings(
      mapper -> {
        mapper.map(Region::getId, RegionDto::setId);
        mapper.map(Region::getName, RegionDto::setName);
      }
    );

    // User
    modelMapper.typeMap(User.class, UserDto.class).addMapping(
      User::isPushAgreed,
      UserDto::setIsPushAgreed
    );

    // Apartment
    modelMapper.typeMap(Apartment.class, ApartmentDto.class).addMapping(
      Apartment::isActive,
      ApartmentDto::setIsActive
    );

    // AdminQuestion
    modelMapper.typeMap(AdminQuestion.class, AdminQuestionDto.class).addMappings(
      mapper -> {
        mapper.map(
          AdminQuestion::isActive,
          AdminQuestionDto::setIsActive
        );
        mapper.map(
          AdminQuestion::getDisplayOn,
          AdminQuestionDto::setDisplayOn
        );
      }
    );

    // AdminQuestionAnswer
    modelMapper.typeMap(AdminQuestionAnswer.class, AdminQuestionAnswerDto.class).<KarrotProfile>addMapping(
      AdminQuestionAnswer::getAnswerer,
      AdminQuestionAnswerDto::setAnswerer
    );

    // Question
    modelMapper.typeMap(Question.class, QuestionDto.class).addMapping(
      Question::getWriter,
      QuestionDto::setWriter
    );

    // Comment
    modelMapper.typeMap(Comment.class, CommentDto.class).addMapping(
      Comment::getWriter,
      CommentDto::setWriter
    );

    return modelMapper;
  }
}
