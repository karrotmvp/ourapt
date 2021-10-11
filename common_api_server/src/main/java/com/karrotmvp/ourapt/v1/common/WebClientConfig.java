package com.karrotmvp.ourapt.v1.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  // TODO: open api 용 webclient bean, oapi 용 webClient bean 두개 분리
  @Bean
  public WebClient webClient() {
    return WebClient.builder().build();
  }
}
