package com.karrotmvp.ourapt.v1.article.vote.repository;

import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VoteCustomRepositoryImpl implements VoteCustomRepository {
  @Override
  public Optional<Vote> findById(String s) {
    return Optional.empty();
  }

  @Override
  public List<Vote> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable) {
    return null;
  }

  @Override
  public List<Vote> findByApartmentIdAndPinned(String apartmentId) {
    return null;
  }

  @Override
  public List<Vote> findByOffsetCursor(Pageable pageable) {
    return null;
  }
}
