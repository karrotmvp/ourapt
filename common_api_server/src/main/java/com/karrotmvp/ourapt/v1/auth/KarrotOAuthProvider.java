package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import com.karrotmvp.ourapt.v1.exception.ThirdPartyApiCallFailException;
import com.karrotmvp.ourapt.v1.user.UserProfileDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class KarrotOAuthProvider {


  @Autowired
  private WebClient webClient;

  @Autowired
  private KarrotProperty karrotProperty;

  public Authentication authenticate(KarrotAuthenticationToken authentication) {
    try {
      UserProfileDto userProfileFromKarrot = asyncGetUserProfileFromKarrot(authentication.getCredentials());
      return new KarrotAuthenticationToken(authentication.getCredentials(), userProfileFromKarrot);
    } catch (ThirdPartyApiCallFailException e) {
      return authentication;
    }
  }

  public UserProfileDto asyncGetUserProfileFromKarrot(String accessToken) {
    Mono<UserProfileDto> userProfileMono = webClient.mutate()
      .defaultHeader(HttpHeaders.AUTHORIZATION, accessToken)
      .build()
      .get()
      .uri(karrotProperty.getBaseUrl())
      .retrieve()
      .onStatus((httpStatus) -> httpStatus.is4xxClientError(), (response)  -> { throw new ThirdPartyApiCallFailException("Fail to call API with 4xx", ""); })
      .onStatus((httpStatus) -> httpStatus.is5xxServerError(), (response)  -> { throw new ThirdPartyApiCallFailException("Fail to call API with 5xx", ""); })
      .bodyToMono(UserProfileDto.class);
    
    return userProfileMono.blockOptional().orElseThrow(() -> new ThirdPartyApiCallFailException("Fail to get profile from karrot", ""));
  }
  
}