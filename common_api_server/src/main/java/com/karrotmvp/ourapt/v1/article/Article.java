package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "article", indexes = {
  @Index(columnList = "writer_id")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Article extends BaseEntity {

  @Id
  @Getter
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id", referencedColumnName = "karrot_id")
  @Getter
  @Setter
  private User writer;

  @Column(name = "main_text")
  @Getter
  private String mainText;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "apartment_id", referencedColumnName = "id")
  @Setter
  @Getter
  private Apartment apartmentWhereCreated;

  @Column(name = "region_id")
  @Setter
  private String regionWhereCreated;

  @Transient
  @Getter
  @Setter
  private int countOfComments = 0;

  @Column(name = "deleted_at")
  @Temporal(TemporalType.TIMESTAMP)
  @Getter
  @Setter
  private Date deletedAt;

  @Column(name = "pinned_until")
  @Getter
  @Setter
  private Date pinnedUntil;


  public Article() {
    super();
  }

  public boolean isPinned() {
    return this.pinnedUntil != null && new Date().before(this.pinnedUntil);
  }

  public boolean isDeleted() {
    return this.deletedAt != null && this.deletedAt.before(new Date());
  }

  public boolean isByAdmin() {
    return this.getWriter().isAdmin();
  }

  public void setMainText(String mainText) {
    this.mainText = mainText;
    this.setUpdatedAt(new Date());
  }


  @PrePersist
  public void generateId() {
    this.id = UUID.randomUUID().toString();
  }

}
