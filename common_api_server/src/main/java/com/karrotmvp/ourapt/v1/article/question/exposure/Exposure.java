package com.karrotmvp.ourapt.v1.article.question.exposure;


import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.article.question.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(
  name = "exposure",
  uniqueConstraints = {
    @UniqueConstraint( columnNames = {"question_id", "expose_to"})
  }
)
@Entity
@NoArgsConstructor
public class Exposure {
  @Id
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id")
  @Getter
  @Setter
  private Question question;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expose_to")
  @Getter
  @Setter
  private Apartment toWhere;

  @Column(name = "pinned_until")
  @Setter
  private Date pinnedUntil;

  public Exposure(Question question, Apartment toWhere) {
    this.id = UUID.randomUUID().toString();
    this.question = question;
    this.toWhere = toWhere;
  }

  public boolean isPinned() {
    return this.pinnedUntil != null && new Date().before(this.pinnedUntil);
  }
}
