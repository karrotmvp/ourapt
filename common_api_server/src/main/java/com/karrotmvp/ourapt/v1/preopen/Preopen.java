package com.karrotmvp.ourapt.v1.preopen;

import com.karrotmvp.ourapt.v1.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "preopen_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Preopen extends BaseEntity {

  @Id
  @Column(name = "karrot_id")
  private String id;

  @Column(name = "push_agreed_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pushAgreedAt;
}
