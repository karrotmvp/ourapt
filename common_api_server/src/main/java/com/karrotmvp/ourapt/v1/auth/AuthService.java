package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotOAuthResponseDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotOpenApiUserDto;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnauthorizedCodeException;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import com.karrotmvp.ourapt.v1.common.exception.security.KarrotInvalidAccessTokenException;
import com.karrotmvp.ourapt.v1.common.karrotopenapi.KarrotOpenApiResponseBody;
import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import io.netty.handler.codec.CodecException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

@Service
public class AuthService {

  private final WebClient karrotOpenApiClient;

  private final KarrotProperty karrotProperty;

  public AuthService(@Qualifier("karrotOpenApiClient") WebClient karrotOpenApiClient, KarrotProperty karrotProperty) {
    this.karrotOpenApiClient = karrotOpenApiClient;
    this.karrotProperty = karrotProperty;
  }

  public KarrotOAuthResponseDto getKarrotAccessToken(String authorizationCode) {
    String karrotApiUrl = UriComponentsBuilder
      .fromUriString(karrotProperty.getOpenApiBaseUrl())
      .path("/oauth/token")
      .queryParam("code", authorizationCode)
      .queryParam("scope", "account/profile")
      .queryParam("grant_type", "authorization_code")
      .queryParam("response_type", "code")
      .build().toUriString();
    return karrotOpenApiClient
      .get()
      .uri(karrotApiUrl)
      .header(HttpHeaders.AUTHORIZATION, assembleBasicToken(
        karrotProperty.getAppId(),
        karrotProperty.getAppSecret()
      ))
      .retrieve()
      .onStatus((httpStatus) -> httpStatus.equals(HttpStatus.UNAUTHORIZED), (response) -> {
        throw new KarrotUnexpectedResponseException("Invalid Karrot APP-ID");
      })
      .onStatus((httpStatus) -> httpStatus.equals(HttpStatus.BAD_REQUEST), (response) -> {
        throw new KarrotUnauthorizedCodeException("Invalid Karrot Authorization code", "");
      })
      .onStatus((httpStatus) -> !httpStatus.equals(HttpStatus.OK), (response) -> {
        throw new KarrotUnexpectedResponseException();
      })
      .bodyToMono(KarrotOAuthResponseDto.class)
      .doOnError(CodecException.class, (ce) -> {
        throw new KarrotUnexpectedResponseException("Cannot deserialize response of Karrot server.", ce);
      })
      .blockOptional()
      .orElseThrow(() -> new KarrotUnexpectedResponseException("Could not find accessToken from response of Karrot Server"));
  }

  public KarrotOpenApiUserDto asyncGetUserProfileFromKarrot(String accessToken) {
    return karrotOpenApiClient
      .get()
      .uri("/api/v1/users/me")
      .header(HttpHeaders.AUTHORIZATION, accessToken)
      .retrieve()
      .onStatus((httpStatus) -> httpStatus.equals(HttpStatus.UNAUTHORIZED), (response) -> {
        throw new KarrotInvalidAccessTokenException("Invalid Karrot AccessToken");
      })
      .onStatus((httpStatus) -> !httpStatus.equals(HttpStatus.OK), (response) -> {
        throw new KarrotUnexpectedResponseException("Unexpected error for calling KarrotAPI");
      })
      .bodyToMono(new ParameterizedTypeReference<KarrotOpenApiResponseBody<KarrotOpenApiUserDto>>() {
      })
      .doOnError(org.springframework.core.codec.CodecException.class, (e) -> {
        throw new KarrotUnexpectedResponseException("Cannot deserialize response of Karrot server.", e);
      })
      .blockOptional()
      .orElseThrow(() -> new KarrotUnexpectedResponseException("Could not find karrotProfile from response of Karrot Server"))
      .getData();
  }

  private String assembleBasicToken(String appId, String appSecret) {
    String basicTokenSource = appId + ":" + appSecret;
    return "Basic " + Base64.getEncoder().encodeToString(basicTokenSource.getBytes());
  }
}
