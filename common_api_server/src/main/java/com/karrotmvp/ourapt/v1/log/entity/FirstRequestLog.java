package com.karrotmvp.ourapt.v1.log.entity;

import com.karrotmvp.ourapt.v1.log.Referer;
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
  @Column(name = "user_id")
  private String userId;

  @Column(name = "created_at")
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "referer")
  @Enumerated(EnumType.STRING)
  private Referer referer;

  public FirstRequestLog(String userId, Referer referer) {
    this.userId = userId;
    this.referer = referer;
  }
}
