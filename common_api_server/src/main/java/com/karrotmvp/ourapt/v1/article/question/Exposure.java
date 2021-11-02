package com.karrotmvp.ourapt.v1.article.question;


import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;

import javax.persistence.*;
import java.util.Date;

@Table(name = "exposure")
@Entity
public class Exposure {
  @Id
  private String id;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @ManyToOne
  @JoinColumn(name = "expose_to")
  private Apartment toWhere;

  @Column(name = "pinned_until")
  private Date pinnedUntil;

  public boolean isPinned() {
    return this.pinnedUntil != null && new Date().before(this.pinnedUntil);
  }
}
