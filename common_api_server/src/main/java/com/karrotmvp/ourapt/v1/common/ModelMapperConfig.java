package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.adminquestion.dto.AdminQuestionDto;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestion;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.article.question.dto.QuestionDto;
import com.karrotmvp.ourapt.v1.user.dto.UserDto;
import com.karrotmvp.ourapt.v1.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        // User
        modelMapper.typeMap(User.class, UserDto.class).addMapping(
                User::isPushAgreed,
                UserDto::setIsPushAgreed
        );

        // AdminQuestion
        modelMapper.typeMap(AdminQuestion.class, AdminQuestionDto.class).addMapping(
                AdminQuestion::isActive,
                AdminQuestionDto::setIsActive
        );
        modelMapper.typeMap(AdminQuestion.class, AdminQuestionDto.class).addMappings(
                mapper -> {
                    mapper.<String>map(
                            (aq) -> aq.getDisplayOn().getId(),
                            (aqdt, regionId) -> aqdt.getDisplayOn().setId(regionId)
                    );
                    mapper.<String>map(
                            (aq) -> aq.getDisplayOn().getName(),
                            (aqdt, regionName) -> aqdt.getDisplayOn().setName(regionName)
                    );
                }
        );

        // Question
        modelMapper.typeMap(Question.class, QuestionDto.class).addMappings(
                mapper -> mapper.<String>map(
                        (question) -> question.getWriter().getId(),
                        (questionDto, karrotId) -> questionDto.getWriter().setId(karrotId)
                )
        );

        return modelMapper;
    }
}
