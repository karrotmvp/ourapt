package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "article")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Article extends BaseEntity {

  @Id
  @Getter
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id", referencedColumnName = "karrot_id")
  @Setter
  private User writer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "apartment_id", referencedColumnName = "id")
  @Setter
  private Apartment apartmentWhereCreated;

  @Column(name = "region_id")
  @Setter
  private String regionWhereCreated;

  public Article() {
    this.id = UUID.randomUUID().toString();
  }

  public KarrotProfile getWriter() {
    return this.writer.getProfile();
  }

  public boolean isByAdmin() {
    return writer.isAdmin();
  }

}
