package com.karrotmvp.ourapt.v1.survey;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "survey")
@Entity
@Getter
@Setter
public class Survey extends BaseEntity {

  @Id
  private String id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "content_json")
  private String contentJson;

  public Survey() {
    this.id = UUID.randomUUID().toString();
  }
}
