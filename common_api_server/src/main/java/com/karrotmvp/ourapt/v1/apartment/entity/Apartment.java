package com.karrotmvp.ourapt.v1.apartment.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "apartment")
@Entity
public class Apartment extends BaseEntity {

  @Id
  @Getter
  private String id;

  @Column(name = "name")
  @Getter
  @Setter
  private String name;

  @Column(name = "brandName")
  @Getter
  @Setter
  private String brandName;

  @Column(name = "bannerImage")
  @Getter
  @Setter
  private String bannerImage;

  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "region_id_depth1")),
    @AttributeOverride(name = "name", column = @Column(name = "region_name_depth1"))
  })
  @Embedded
  @Column(name = "region_depth1")
  @Getter
  @Setter
  private Region regionDepth1;

  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "region_id_depth2")),
    @AttributeOverride(name = "name", column = @Column(name = "region_name_depth2")),
  })
  @Embedded
  @Column(name = "region_depth2")
  @Getter
  @Setter
  private Region regionDepth2;

  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "region_id_depth3")),
    @AttributeOverride(name = "name", column = @Column(name = "region_name_depth3")),
  })
  @Embedded
  @Column(name = "region_depth3")
  @Getter
  @Setter
  private Region regionDepth3;

  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "region_id_depth4")),
    @AttributeOverride(name = "name", column = @Column(name = "region_name_depth4")),
  })
  @Embedded
  @Column(name = "region_depth4")
  @Getter
  @Setter
  private Region regionDepth4;

  @Column(name = "inactive_at")
  @Temporal(TemporalType.TIMESTAMP)
  @Setter
  private Date inactiveAt;

  public Apartment() {
    super();
    this.id = UUID.randomUUID().toString();
  }

  public Boolean isActive() {
    return this.inactiveAt == null || this.inactiveAt.after(new Date());
  }
}
