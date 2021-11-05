package com.karrotmvp.ourapt.v1.article.question.repository;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface QuestionCustomRepository<T, ID> {

  Optional<T> findById(ID id);
  List<T> findFirstByExposedToAndDateCursorByOrderByDesc(String exposedTo, Date dateCursor, Pageable pageable);
  List<T> findByExposurePinnedAndToWhere(String toWhereApartmentId);
}
