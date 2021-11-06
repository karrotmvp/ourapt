package com.karrotmvp.ourapt.v1.article.question.repository;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface QuestionCustomRepository<T, ID> {

  Optional<T> findById(ID id);
  List<T> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable);
  List<T> findByApartmentIdAndPinned(String toWhereApartmentId);
  List<T> findByOffsetCursor(Pageable pageable);
}
