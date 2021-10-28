package com.karrotmvp.ourapt.v1.user.entity;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {
  
  @Id
  @Column(name = "karrot_id", nullable = false)
  @Getter
  private String id;

  @Transient
  @Getter
  private KarrotProfile profile;

  @Column(name = "push_agreed_at")
  @Temporal(TemporalType.TIMESTAMP)
  @Getter
  @Setter
  private Date pushAgreedAt;

  @Column(name = "deleted_at")
  @Temporal(TemporalType.TIMESTAMP)
  @Setter
  private Date deletedAt;

  public boolean isDeleted() {
    return this.deletedAt != null && deletedAt.before(new Date());
  }
  public User(String userId, KarrotProfile profile) {
    this.id = userId;
    this.profile = profile;
  };
}

