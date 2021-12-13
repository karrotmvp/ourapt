package com.karrotmvp.ourapt.v1.article.vote.entity;

import com.karrotmvp.ourapt.v1.article.Article;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue(value = "V")
public class Vote extends Article {

  @BatchSize(size = 10)
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
  @Getter
  private List<VoteItem> items = new ArrayList<>();

  @Column(name = "pinned_until")
  @Getter
  @Setter
  private Date terminatedAt;

  public boolean isInProgress() {
    return this.terminatedAt != null && new Date().before(this.terminatedAt);
  }

  public Vote() {
    super();
  }

  public void sortItems() {
    this.items.sort(Comparator.comparingInt(VoteItem::getOrderInParent));
  }
}

