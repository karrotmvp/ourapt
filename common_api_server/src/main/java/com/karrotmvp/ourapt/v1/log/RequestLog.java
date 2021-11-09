package com.karrotmvp.ourapt.v1.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

  @Column(name = "path")
  private String path;

  @Column(name = "region_id")
  private String regionId;

  @Column(name = "instance_id")
  private String instanceId;

  @Column(name = "created_at")
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
