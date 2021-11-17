package com.karrotmvp.ourapt.v1.article.repository;

import com.karrotmvp.ourapt.v1.article.Article;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ArticleCustomRepository<T extends Article, ID> {

  Optional<T> findById(ID id, Class<T> resultClass);
  List<T> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable, Class<T> resultClass);
  List<T> findByApartmentIdAndPinned(String apartmentId, Class<T> resultClass);
  List<T> findByOffsetCursor(Pageable pageable, Class<T> resultClass);
}
