package com.karrotmvp.ourapt.v1.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Table(name = "first_request_log")
@Entity
@NoArgsConstructor
@Getter
public class FirstRequestLog {

  @Id
  @Column(name = "app_instance_id")
  private String instanceId;

  @Column(name = "created_at")
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "referer")
  @Enumerated(EnumType.STRING)
  private Referer referer;

  public FirstRequestLog(String instanceId, Referer referer) {
    this.instanceId = instanceId;
    this.referer = referer;
  }
}
