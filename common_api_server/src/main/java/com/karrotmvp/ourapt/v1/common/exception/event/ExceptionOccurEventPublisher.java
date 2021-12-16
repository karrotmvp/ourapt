package com.karrotmvp.ourapt.v1.common.exception.event;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExceptionOccurEventPublisher {
  private final ApplicationEventPublisher applicationEventPublisher;

  public void publish(final String traceHint, final Exception exception) {
    ExceptionOccurEvent event = new ExceptionOccurEvent(this, traceHint, exception);
    applicationEventPublisher.publishEvent(event);
  }
}
