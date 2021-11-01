package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.common.BaseEntity;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.exception.application.NotFoundInRegionDictException;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;
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

  @Column(name = "region_id")
  @Setter
  private String createdOn;

  public Article() {
    this.id = UUID.randomUUID().toString();
  }

  public KarrotProfile getWriter() {
    return this.writer.getProfile();
  }

  public Region getCreatedOn() {
    return Optional.ofNullable(Static.regionDict.get(this.createdOn))
      .orElseThrow(() -> new NotFoundInRegionDictException(this.createdOn));
  }

  public boolean isByAdmin() {
    return writer.getIsAdmin();
  }

}
