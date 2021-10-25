package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String> {

    @Query("SELECT q FROM Question q " +
            "WHERE q.createdAt < :dateCursor " +
            "ORDER BY q.createdAt DESC")
    List<Question> findByDateCursorWithPaging(
            Date dateCursor,
            Pageable pageable
    );

    List<Question> findByOrderByCreatedAtDesc(Pageable pageable);

    long countByMainTextNot(String mainText);

}
