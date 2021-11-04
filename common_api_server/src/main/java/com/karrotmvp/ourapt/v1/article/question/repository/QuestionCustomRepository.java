package com.karrotmvp.ourapt.v1.article.question.repository;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface QuestionCustomRepository<T, ID> {

  List<T> findFirstByExposedToAndDateCursorByOrderByDesc(String exposedTo, Date dateCursor, Pageable pageable);
  List<T> findByExposurePinnedAndToWhere(String toWhereApartmentId);
}
