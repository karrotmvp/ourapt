package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "article", indexes = {
  @Index(columnList = "writer_id"),
  @Index(columnList = "parent_id")
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "apartment_id", referencedColumnName = "id")
  @Setter
  private Apartment apartmentWhereCreated;

  @Column(name = "region_id")
  @Setter
  private String regionWhereCreated;

  @Transient
  @Getter
  @Setter
  private int countOfComments = 0;

  public Article() {
    this.id = UUID.randomUUID().toString();
  }
}
