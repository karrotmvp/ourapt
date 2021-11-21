package com.karrotmvp.ourapt.v1.article.vote.entity;

import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@DiscriminatorValue(value = "V")
public class Vote extends Article {

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
  @Getter
  private List<VoteItem> items = new ArrayList<>();

  public Vote() {
    super();
  }

  public void sortItems() {
    this.items.sort(Comparator.comparingInt(VoteItem::getOrderInParent));
  }
}

