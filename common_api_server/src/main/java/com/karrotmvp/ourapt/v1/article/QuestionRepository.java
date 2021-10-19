package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Article, String> {
}
