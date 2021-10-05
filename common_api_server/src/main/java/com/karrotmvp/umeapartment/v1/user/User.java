package com.karrotmvp.umeapartment.v1.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.karrotmvp.umeapartment.v1.common.BaseEntity;

import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class User extends BaseEntity {
  
  @Id
  private String id;

  @Column(name = "daangn_id", unique = true, nullable = false)
  private String daangnId;
  
  @Column(name = "nickname", nullable = false)
  private String nickname;

  @Column(name = "profile_image_url", nullable = true)
  private String profileImageUrl;

  @Column(name = "phone_number", unique = true, nullable = false) // TODO: 'nullable false' is correct?
  private String phoneNumber;

  @Column(name = "push_agreed_at", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date pushAgreedAt;
}

