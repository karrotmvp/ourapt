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

  @Column(name = "app_instance_id")
  private String instanceId;

  @Column(name = "answer")
  private String answer;

  @Column(name = "region_id")
  private String regionId;

  public NoApartment(String instanceId, String answer, String regionId) {
    this.id = UUID.randomUUID().toString();
    this.instanceId = instanceId;
    this.answer = answer;
    this.regionId = regionId;
  }
}
