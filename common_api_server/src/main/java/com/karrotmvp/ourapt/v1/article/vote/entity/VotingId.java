package com.karrotmvp.ourapt.v1.article.vote.entity;


import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VotingId implements Serializable {
  private String votedBy;
  private String voteFor;
}
