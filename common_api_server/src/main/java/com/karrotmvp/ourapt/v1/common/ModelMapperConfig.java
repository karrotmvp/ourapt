package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.model.RegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.article.comment.Comment;
import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionWithWhereCreatedDto;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
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

    // Apartment
    modelMapper.typeMap(Apartment.class, ApartmentDto.class).addMapping(
      Apartment::isActive,
      ApartmentDto::setActive
    );

    // User
    modelMapper.typeMap(User.class, UserDto.class).addMappings(
      mapper -> {
        mapper.map(
          User::isPushAgreed,
          UserDto::setIsPushAgreed
        );
      }
    );


    // Question
    modelMapper.typeMap(Question.class, QuestionDto.class).addMappings(
      mapper -> {
        mapper.map((q) -> q.getWriter().getProfile(), QuestionDto::setWriter);
        mapper.map(Question::isByAdmin, QuestionDto::setByAdmin);
        mapper.map(Question::isPinned, QuestionDto::setIsPinned);
      }
    );
    modelMapper.typeMap(Question.class, QuestionWithWhereCreatedDto.class).addMappings(
      mapper -> {
        mapper.map((q) -> q.getWriter().getProfile(), QuestionWithWhereCreatedDto::setWriter);
        mapper.map(Question::isByAdmin, QuestionWithWhereCreatedDto::setByAdmin);
        mapper.map(Question::isPinned, QuestionWithWhereCreatedDto::setIsPinned);
      }
    );

    // Comment
    modelMapper.typeMap(Comment.class, CommentDto.class).addMapping(
      (c) -> c.getWriter().getProfile(),
      CommentDto::setWriter
    );

    return modelMapper;
  }
}
