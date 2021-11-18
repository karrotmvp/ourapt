package com.karrotmvp.ourapt.v1.article.vote.repository;

import com.karrotmvp.ourapt.v1.article.vote.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteItemRepository extends JpaRepository<VoteItem, String> {
}
