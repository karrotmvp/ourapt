package com.karrotmvp.ourapt.v1.article;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ArticleCustomRepository<T extends Article, ID> {

  Optional<T> findById(ID id);
  List<T> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable); List<T> findByApartmentIdAndPinned(String apartmentId);
  List<T> findByOffsetCursor(Pageable pageable);
}
