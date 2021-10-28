package com.karrotmvp.ourapt.v1.common;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Getter
  @Setter
  private Date createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Getter
  @Setter
  private Date updatedAt;
  
}
