package com.karrotmvp.ourapt.v1.article.vote.entity;

import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "V")
public class Vote extends Article {

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
  @Getter
  private Set<VoteItem> items = new HashSet<>();

  public Vote() {
    super();
  }
}

