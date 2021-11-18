package com.karrotmvp.ourapt.v1.article.vote.entity;

import com.karrotmvp.ourapt.v1.user.entity.User;

import javax.persistence.*;

@Table(name = "voting")
@Entity
@IdClass(VotingId.class)
public class Voting {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "voted_by")
  private User votedBy;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vote_for")
  private VoteItem voteFor;
}
