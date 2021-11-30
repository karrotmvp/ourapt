package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.ArticleCustomRepository;
import com.karrotmvp.ourapt.v1.article.question.Question;

import java.util.List;

public interface QuestionCustomRepository extends ArticleCustomRepository<Question, String> {
  List<Question> findByAboutId(String voteId);
}
