package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.article.question.dto.QuestionDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper =  new ModelMapper();
        modelMapper.typeMap(Question.class, QuestionDto.class).addMappings(
                mapper -> mapper.map(
                        (question) -> question.getWriter().getKarrotId(),
                        (questionDto, karrotId)  -> questionDto.getWriter().setId(String.valueOf(karrotId))
                )
        );
        return modelMapper;
    }
}
