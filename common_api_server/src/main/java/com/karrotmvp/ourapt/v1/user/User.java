package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
@Entity
@Getter
@Setter
public class User extends BaseEntity {
  
  @Id
  @Column(name = "karrot_id", unique = true, nullable = false)
  private String karrotId;

  @Column(name = "push_agreed_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pushAgreedAt;
}

