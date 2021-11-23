package com.karrotmvp.ourapt.v1.article.vote.entity;

import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

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

  @BatchSize(size = 10)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
  @Getter
  private List<VoteItem> items = new ArrayList<>();

  public Vote() {
    super();
  }

  public void sortItems() {
    this.items.sort(Comparator.comparingInt(VoteItem::getOrderInParent));
  }
}

