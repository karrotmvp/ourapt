package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.common.KarrotResponseBody;
import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import com.karrotmvp.ourapt.v1.exception.ThirdPartyApiCallFailException;
import com.karrotmvp.ourapt.v1.user.UserProfileDto;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@AllArgsConstructor
public class KarrotOAuthProvider implements AuthenticationProvider {
    // Spring Security loads all the 'AuthenticationProvider Beans' when it initializes ProviderManager(AuthenticationManager)

    @Autowired
    private WebClient webClient;

    @Autowired
    private KarrotProperty karrotProperty;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserProfileDto asyncGetUserProfileFromKarrot(String accessToken) {
        logger.info("KARROT_API_CALLED");
        Mono<KarrotResponseBody<UserProfileDto>> userProfileMono = webClient.mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, accessToken)
                .build()
                .get()
                .uri(karrotProperty.getBaseUrl() + "/api/v1/users/me")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<KarrotResponseBody<UserProfileDto>>() {
                });
        Optional<KarrotResponseBody<UserProfileDto>> optionalUserProfile = userProfileMono.blockOptional();
        //TODO: API 결과에 따라서 line 57 에서 받을 수 있도록 다른 Exception 던지도록 수정
        return optionalUserProfile.orElseThrow(() -> new ThirdPartyApiCallFailException("Fail to get profile from karrot", "")).getData();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            UserProfileDto userProfileFromKarrot = asyncGetUserProfileFromKarrot(String.valueOf(authentication.getCredentials()));
//            UserProfileDto userProfileFromKarrot = new UserProfileDto("userId", "nickname", "imageUrl", "phoneNumber"); // TODO: FOR TEST AUTHENTICATION
            return createSuccessAuthentication(authentication, userProfileFromKarrot);
        } catch (Exception e) {
            // TODO: 던지는 Exception 이 무엇이냐에 따라  - 1.API 요청하는 코드 혹은 당근 서버쪽 에러 (500, 200인데 에러), 2.사용자 AccessToken 에러 - 핸들링 필요)
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return KarrotAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication createSuccessAuthentication(Authentication authentication, UserProfileDto karrotUserProfile) {
        return new KarrotAuthenticationToken(String.valueOf(authentication.getCredentials()), karrotUserProfile);
    }


}