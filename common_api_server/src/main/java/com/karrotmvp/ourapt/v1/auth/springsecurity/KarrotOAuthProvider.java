package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.karrotmvp.ourapt.v1.auth.AuthService;
import com.karrotmvp.ourapt.v1.common.exception.security.KarrotInvalidAccessTokenException;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Profile({"production | alpha"})
@AllArgsConstructor
public class KarrotOAuthProvider implements AuthenticationProvider {
    // Spring Security loads all the 'AuthenticationProvider Beans' when it initializes ProviderManager(AuthenticationManager)

    @Autowired
    private AuthService authService;

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KarrotOpenApiUserProfileDto checkAccessTokenToKarrotAuthServer(String accessToken) {
        return authService.asyncGetUserProfileFromKarrot(accessToken);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            KarrotOpenApiUserProfileDto userProfileFromKarrot = checkAccessTokenToKarrotAuthServer(String.valueOf(authentication.getCredentials()));
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

    private Authentication createSuccessAuthentication(Authentication authentication, KarrotOpenApiUserProfileDto karrotUserProfile) {
        return new KarrotAuthenticationToken(String.valueOf(authentication.getCredentials()), karrotUserProfile);
    }


}