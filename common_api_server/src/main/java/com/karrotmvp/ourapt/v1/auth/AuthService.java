package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotAccessTokenDto;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnauthorizedCode;
import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private KarrotProperty karrotProperty;

    public KarrotAccessTokenDto getKarrotAccessToken(String authorizationCode) {
        String karrotApiUrl = UriComponentsBuilder.fromUriString(karrotProperty.getOpenApiBaseUrl())
                .path("/oauth/token")
                .queryParam("code", authorizationCode)
                .queryParam("scope", "account/profile")
                .queryParam("grant_type", "authorization_code")
                .queryParam("response_type", "code")
                .build().toUriString();
        Mono<KarrotAccessTokenDto> responseMono = webClient
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
                    throw new KarrotUnauthorizedCode("Karrot" , "");
                })
                .bodyToMono(KarrotAccessTokenDto.class);
        return responseMono.blockOptional()
                .orElseThrow(() -> new KarrotUnauthorizedCode(
                        "KARROT 서버로 부터 액세스 토큰을 발급 받는 것에 실패했습니다.",
                        ""));
    }

    private String assembleBasicToken(String appId, String appSecret) {
        String basicTokenSource = appId + ":" + appSecret;
        return Base64.getEncoder().encodeToString(basicTokenSource.getBytes());
    }


}
