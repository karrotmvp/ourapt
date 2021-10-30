package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.karrotmvp.ourapt.v1.auth.AuthService;
import com.karrotmvp.ourapt.v1.common.exception.security.KarrotInvalidAccessTokenException;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Profile({"production | alpha"})
public class KarrotOAuthProvider implements AuthenticationProvider {
    // Spring Security loads all the 'AuthenticationProvider Beans' when it initializes ProviderManager(AuthenticationManager)

    private final AuthService authService;

    public KarrotOAuthProvider(AuthService authService) {
        this.authService = authService;
    }

    public KarrotOpenApiUserDto checkAccessTokenToKarrotAuthServer(String accessToken) {
        return authService.asyncGetUserProfileFromKarrot(accessToken);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            KarrotOpenApiUserDto userProfileFromKarrot = checkAccessTokenToKarrotAuthServer(String.valueOf(authentication.getCredentials()));
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

    private Authentication createSuccessAuthentication(Authentication authentication, KarrotOpenApiUserDto karrotUserProfile) {
        return new KarrotAuthenticationToken(String.valueOf(authentication.getCredentials()), karrotUserProfile);
    }


}