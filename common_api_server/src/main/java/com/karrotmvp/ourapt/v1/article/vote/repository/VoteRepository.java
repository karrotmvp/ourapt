package com.karrotmvp.ourapt.v1.article.vote.repository;


import com.karrotmvp.ourapt.v1.article.ArticleRepository;
import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;


public interface VoteRepository extends ArticleRepository<Vote>, VoteCustomRepository {
}
