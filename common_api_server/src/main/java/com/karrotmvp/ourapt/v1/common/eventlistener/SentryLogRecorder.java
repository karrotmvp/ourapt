package com.karrotmvp.ourapt.v1.common.eventlistener;

import com.karrotmvp.ourapt.v1.common.exception.event.ExceptionOccurEvent;
import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SentryLogRecorder {

  @Value("${spring.profiles.active}")
  private String activeProfile;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @EventListener(ExceptionOccurEvent.class)
  public void handleExceptionOccurEvent(ExceptionOccurEvent event)  {
    Sentry.captureException(event.getException());
  }
}
