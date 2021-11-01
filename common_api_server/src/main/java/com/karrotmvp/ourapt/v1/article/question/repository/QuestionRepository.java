package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.question.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String>{



    // TODO: do custom
    List<Question> findByOrderByCreatedAtDesc(Pageable pageable);

    long countByMainTextNot(String mainText);
}
