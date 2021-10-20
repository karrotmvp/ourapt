package com.karrotmvp.ourapt.v1.common.webclient;

import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Autowired
  private WebClient.Builder webClientBuilder;

  @Autowired
  private KarrotProperty karrotProperty;

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

}
