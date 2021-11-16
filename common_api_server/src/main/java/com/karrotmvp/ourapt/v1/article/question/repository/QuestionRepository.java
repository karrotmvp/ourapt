package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;


public interface QuestionRepository extends JpaRepository<Question, String>, QuestionCustomRepository<Question, String>{
  long countByDeletedAtIsNull();
  long countByCreatedAtBetween(Date startDate, Date endDate);
}
