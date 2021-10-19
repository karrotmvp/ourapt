package com.karrotmvp.ourapt.v1.auth;

import java.util.Base64;
import java.util.Optional;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotOAuthResponseDto;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotUserProfile;
import com.karrotmvp.ourapt.v1.common.KarrotResponseBody;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnauthorizedCode;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import com.karrotmvp.ourapt.v1.common.exception.security.KarrotInvalidAccessTokenException;
import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import io.netty.handler.codec.CodecException;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private KarrotProperty karrotProperty;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KarrotOAuthResponseDto getKarrotAccessToken(String authorizationCode) {
        String karrotApiUrl = UriComponentsBuilder.fromUriString(karrotProperty.getOpenApiBaseUrl())
                .path("/oauth/token")
                .queryParam("code", authorizationCode)
                .queryParam("scope", "account/profile")
                .queryParam("grant_type", "authorization_code")
                .queryParam("response_type", "code")
                .build().toUriString();
        Mono<KarrotOAuthResponseDto> responseMono = webClient
                .mutate()
                .build()
                .get()
                .uri(karrotApiUrl)
                .headers(headers -> {
                            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                            headers.add(HttpHeaders.AUTHORIZATION, assembleBasicToken(
                                    karrotProperty.getAppId(),
                                    karrotProperty.getAppSecret()
                            ));
                        }
                )
                .retrieve()
                .onStatus((httpStatus) -> httpStatus.equals(HttpStatus.UNAUTHORIZED), (response) -> {
                    throw new KarrotUnexpectedResponseException("잘못된 KARROT APP ID 입니다.");
                })
                .onStatus((httpStatus) -> httpStatus.equals(HttpStatus.BAD_REQUEST), (response) -> {
                    throw new KarrotUnauthorizedCode("유효하지 않은 KARROT authorization code 입니다." , "");
                })
                .onStatus((httpStatus) -> !httpStatus.equals(HttpStatus.OK), (response) -> {
                    throw new KarrotUnexpectedResponseException("KARROT 서버로 부터 정상적이지 않은 응답을 받았습니다.");
                })
                .bodyToMono(KarrotOAuthResponseDto.class);
        try {
            return responseMono.blockOptional()
                    .orElseThrow(() -> new KarrotUnexpectedResponseException("KARROT API 응답으로부터 엑세스 토큰을 찾을 수 없습니다."));
        } catch(CodecException ce) {
            throw new KarrotUnexpectedResponseException("Karrot 서버의 응답을 역직렬화 할 수 없음", ce);
        }
    }

    public KarrotUserProfile asyncGetUserProfileFromKarrot(String accessToken) {
        Mono<KarrotResponseBody<KarrotUserProfile>> userProfileMono = webClient.mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, accessToken)
                .build()
                .get()
                .uri(karrotProperty.getOpenApiBaseUrl() + "/api/v1/users/me")
                .retrieve()
                .onStatus((httpStatus) -> httpStatus.equals(HttpStatus.UNAUTHORIZED), (response) -> {
                    throw new KarrotInvalidAccessTokenException("유효하지 않은 Karrot AccessToken 입니다");
                })
                .onStatus((httpStatus) -> !httpStatus.equals(HttpStatus.OK), (response) -> {
                    throw new KarrotUnexpectedResponseException(
                            "KARROT API 호출 중 예상치 못한 오류");
                })
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        try {
            Optional<KarrotResponseBody<KarrotUserProfile>> userProfileOptional = userProfileMono.blockOptional();
            return userProfileOptional
                    .orElseThrow(() -> new KarrotUnexpectedResponseException("KARROT API 응답으로 부터 유저 프로필 찾을 수 없음"))
                    .getData();
        } catch (org.springframework.core.codec.CodecException e) {
            throw new KarrotUnexpectedResponseException("Karrot 서버의 응답을 역직렬화 할 수 없음", e);
        }
    }

    private String assembleBasicToken(String appId, String appSecret) {
        String basicTokenSource = appId + ":" + appSecret;
        return "Basic " + Base64.getEncoder().encodeToString(basicTokenSource.getBytes());
    }
}
