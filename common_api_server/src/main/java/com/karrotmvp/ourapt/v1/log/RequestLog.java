package com.karrotmvp.ourapt.v1.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "request_log")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class RequestLog {

  @Id
  private Long id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "region_id")
  private String regionId;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
