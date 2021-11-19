package com.karrotmvp.ourapt.v1.common.eventlistener;

import com.karrotmvp.ourapt.v1.common.exception.event.ExceptionOccurEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackWebhookSender {

  @Value("${spring.profiles.active}")
  private String activeProfile;
  private final WebClient slackWebhookClient;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public SlackWebhookSender(@Qualifier("slackWebhookClient") WebClient slackWebhookClient) {
    this.slackWebhookClient = slackWebhookClient;
  }

  @EventListener(ExceptionOccurEvent.class)
  public void handleExceptionOccurEvent(ExceptionOccurEvent event) {
    this.sendWebhook("[" + activeProfile + "] " + event.getSummary() + "(" + event.getEventAt() + ")");
  }

  private void sendWebhook(String message) {
    SlackWebhook body = new SlackWebhook(message);
    this.slackWebhookClient
      .post()
      .bodyValue(body)
      .retrieve()
      .onStatus(status -> !status.is2xxSuccessful(), (resp) ->
        {
          String msg = resp.bodyToMono(String.class).block();
          logger.error("Sending slackWebhook fail: " + msg);
          throw new RuntimeException("slack webhook fail");
        }
      )
      .bodyToMono(String.class)
      .block();
  }

  @AllArgsConstructor
  @Getter
  private static class SlackWebhook {
    private String text;
  }
}
