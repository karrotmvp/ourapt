package com.karrotmvp.ourapt.v1.article.repository;

import com.karrotmvp.ourapt.v1.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ArticleRepository<T extends Article> extends JpaRepository<T, String>, ArticleCustomRepository<T, String> {
  long countByDeletedAtIsNull();
  long countByCreatedAtBetween(Date startDate, Date endDate);
}
