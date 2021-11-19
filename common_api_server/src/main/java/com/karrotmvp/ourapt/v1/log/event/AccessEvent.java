package com.karrotmvp.ourapt.v1.log.event;

import com.karrotmvp.ourapt.v1.log.vo.LogVo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Date;


public class AccessEvent extends ApplicationEvent {

  @Getter
  private LogVo log;

  @Getter
  private Date eventAt;

  public AccessEvent(Object source, LogVo log) {
    super(source);
    this.log = log;
    this.eventAt = new Date();
  }
}
