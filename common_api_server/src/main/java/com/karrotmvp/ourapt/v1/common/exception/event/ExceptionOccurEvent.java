package com.karrotmvp.ourapt.v1.common.exception.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Date;

public class ExceptionOccurEvent extends ApplicationEvent {
  @Getter
  private String message;

  @Getter
  private String traceHint;

  @Getter
  private Date eventAt;

  public ExceptionOccurEvent(Object source, String traceHint, String message) {
    super(source);
    this.message = message;
    this.traceHint = traceHint;
    this.eventAt = new Date();
  }

  public String getSummary() {
    return "[ThrownException] -> " + this.getMessage() + " IN " + this.getTraceHint();
  }
}
