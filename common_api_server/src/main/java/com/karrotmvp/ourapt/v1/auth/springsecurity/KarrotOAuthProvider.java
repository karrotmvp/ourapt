package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.karrotmvp.ourapt.v1.common.KarrotResponseBody;
import com.karrotmvp.ourapt.v1.common.exception.security.KarrotInvalidAccessTokenException;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.codec.CodecException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

@Component
@AllArgsConstructor
public class KarrotOAuthProvider implements AuthenticationProvider {
    // Spring Security loads all the 'AuthenticationProvider Beans' when it initializes ProviderManager(AuthenticationManager)

    @Autowired
    private WebClient webClient;

    @Autowired
    private KarrotProperty karrotProperty;

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KarrotUserProfileDto asyncGetUserProfileFromKarrot(String accessToken) {
        if (!Arrays.asList(env.getActiveProfiles()).contains("production")) {
            if (accessToken.equals("Beaerer ")) {
            }
            return new KarrotUserProfileDto("userIdExample", "userNicknameExample");
        }
        logger.info("KARROT_API_CALLED");
        Mono<KarrotResponseBody<KarrotUserProfileDto>> userProfileMono = webClient.mutate()
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
                .bodyToMono(new ParameterizedTypeReference<KarrotResponseBody<KarrotUserProfileDto>>() {
                });
        try {
            Optional<KarrotResponseBody<KarrotUserProfileDto>> userProfileOptional = userProfileMono.blockOptional();
            return userProfileOptional
                    .orElseThrow(() -> new KarrotUnexpectedResponseException("KARROT API 응답으로 부터 유저 프로필 찾을 수 없음"))
                    .getData();
        } catch (CodecException e) {
            throw new KarrotUnexpectedResponseException("Karrot 서버의 응답을 역직렬화 할 수 없음", e);
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            KarrotUserProfileDto userProfileFromKarrot = asyncGetUserProfileFromKarrot(String.valueOf(authentication.getCredentials()));
            return createSuccessAuthentication(authentication, userProfileFromKarrot);
        } catch (KarrotInvalidAccessTokenException exception) {
            throw new BadCredentialsException(exception.getMessage());
        } catch (Exception exception) {
            throw new AuthenticationServiceException(exception.getMessage());
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return KarrotAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication createSuccessAuthentication(Authentication authentication, KarrotUserProfileDto karrotUserProfile) {
        return new KarrotAuthenticationToken(String.valueOf(authentication.getCredentials()), karrotUserProfile);
    }


}