package com.karrotmvp.ourapt.v1.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.karrotmvp.ourapt.v1.common.BaseEntity;

import lombok.Getter;

@Table(name = "user")
@Entity
@Getter
public class User extends BaseEntity {
  
  @Id
  @Column(name = "karrot_id", unique = true, nullable = false)
  private String karrotId;

  @Column(name = "push_agreed_at", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date pushAgreedAt;
}

