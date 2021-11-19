package com.karrotmvp.ourapt.v1.common.webclient;

import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  private final WebClient.Builder webClientBuilder;

  private final KarrotProperty karrotProperty;

  public WebClientConfig(WebClient.Builder webClientBuilder, KarrotProperty karrotProperty) {
    this.webClientBuilder = webClientBuilder;
    this.karrotProperty = karrotProperty;
  }

  @Bean
  public WebClient karrotOApiClient() {
    return this.webClientBuilder
            .baseUrl(karrotProperty.getOApiBaseUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("X-Api-Key", karrotProperty.getApiKey())
            .build();
  }

  @Bean
  public WebClient karrotOpenApiClient() {
    return this.webClientBuilder
            .baseUrl(karrotProperty.getOpenApiBaseUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
  }

  @Bean
  public WebClient slackWebhookClient() {
    return this.webClientBuilder
      .baseUrl("https://hooks.slack.com/services/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")
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .build();
  }

}
