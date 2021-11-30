package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.ArticleRepository;
import com.karrotmvp.ourapt.v1.article.question.Question;

import java.util.List;

public interface QuestionRepository extends ArticleRepository<Question>, QuestionCustomRepository {
}
