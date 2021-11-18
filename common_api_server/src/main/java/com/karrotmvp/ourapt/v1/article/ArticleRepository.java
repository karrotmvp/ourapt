package com.karrotmvp.ourapt.v1.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ArticleRepository<T extends Article> extends JpaRepository<T, String>{

  long countByDeletedAtIsNull();
  long countByCreatedAtBetween(Date startDate, Date endDate);
}
