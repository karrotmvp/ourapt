package com.karrotmvp.ourapt.v1.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConstructorBinding
@ConfigurationProperties(prefix="karrot.api")
@RequiredArgsConstructor
@Getter
public class KarrotProperty {
  private final String appId;
  private final String appSecret;
  private final String apiKey;
  private final String openApiBaseUrl;
  private final String oApiBaseUrl;
}
