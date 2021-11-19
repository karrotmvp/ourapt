package com.karrotmvp.ourapt.v1.log.event;

import com.karrotmvp.ourapt.v1.log.vo.LogVo;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccessEventPublisher {
  private final ApplicationEventPublisher applicationEventPublisher;
  public void publish(final LogVo log) {
    AccessEvent event = new AccessEvent(this, log);
    applicationEventPublisher.publishEvent(event);
  }

}
