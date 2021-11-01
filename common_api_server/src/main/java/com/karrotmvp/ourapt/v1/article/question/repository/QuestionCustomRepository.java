package com.karrotmvp.ourapt.v1.article.question.repository;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface QuestionCustomRepository<T, ID> {
  List<T> findFirstByDateCursorByOrderByDesc(Date dateCursor, Pageable pageable);
}
