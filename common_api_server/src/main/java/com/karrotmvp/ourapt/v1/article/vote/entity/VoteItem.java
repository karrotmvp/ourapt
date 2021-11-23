package com.karrotmvp.ourapt.v1.article.vote.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "vote_item")
@Entity
@NoArgsConstructor
public class VoteItem {

  @Id
  @Getter
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  @Getter
  private Vote parent;

  @BatchSize(size = 10)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "voteFor")
  @Setter
  private List<Voting> votings;

  @Column(name = "main_text")
  @Setter
  @Getter
  private String mainText;

  @Column(name = "order_in_parent")
  @Setter
  @Getter
  private int orderInParent;

  public VoteItem(String mainText, int orderInParent) {
    this.mainText = mainText;
    this.orderInParent = orderInParent;
  }

  public void setParent(Vote parent) {
    this.parent = parent;
    parent.getItems().add(this);
  }

  @PrePersist
  public void generateId() {
    this.id = UUID.randomUUID().toString();
  }

  public List<String> getVoterIds() {
    return this.votings.stream().map(voting -> voting.getVotedBy().getId()).collect(Collectors.toList());
  }
}
