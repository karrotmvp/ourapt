package com.karrotmvp.ourapt.v1.log.entity;

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
  @GeneratedValue
  private Long id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "method")
  private String method;

  @Column(name = "path")
  private String path;

  @Column(name = "region_id")
  private String regionId;

  @Column(name = "app_instance_id")
  private String instanceId;

  @Column(name = "res_status")
  private int status;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

}
