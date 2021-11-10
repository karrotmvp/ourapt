package com.karrotmvp.ourapt.v1.noapt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "no_apartment")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoApartment {
  @Id
  private String id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "answer")
  private String answer;

  @Column(name = "region_id")
  private String regionId;

  public NoApartment(String answer, String regionId, String userId) {
    this.id = UUID.randomUUID().toString();
    this.answer = answer;
    this.regionId = regionId;
    this.userId = userId;
  }
}
