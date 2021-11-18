package com.karrotmvp.ourapt.v1.article.vote.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "voting")
@Entity
@IdClass(VotingId.class)
@Setter
@Getter
public class Voting extends BaseEntity {

  @Id
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "voted_by")
  private User votedBy;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vote_for")
  private VoteItem voteFor;
}
