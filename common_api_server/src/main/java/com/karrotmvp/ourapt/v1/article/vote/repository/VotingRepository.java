package com.karrotmvp.ourapt.v1.article.vote.repository;

import com.karrotmvp.ourapt.v1.article.vote.entity.Voting;
import com.karrotmvp.ourapt.v1.article.vote.entity.VotingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotingRepository extends JpaRepository<Voting, VotingId> {
  List<Voting> findByVoteForIdIn(List<String> voteItemIds);
}
