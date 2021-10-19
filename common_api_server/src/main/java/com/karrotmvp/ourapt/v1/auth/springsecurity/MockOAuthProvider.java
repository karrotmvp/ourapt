package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.karrotmvp.ourapt.v1.auth.AuthService;
import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile({"!production"})
public class MockOAuthProvider extends KarrotOAuthProvider {
    // MockOAuthProvider is a Bean that is injected instead of KarrotOAuthProvider when the Profile is not production
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MockOAuthProvider(WebClient webClient, KarrotProperty karrotProperty, AuthService authService, Environment env) {
        super(webClient, karrotProperty, authService, env);
    }

    @Override
    public KarrotUserProfile checkAccessTokenToKarrotAuthServer(String accessToken) {
        String mockSeed = accessToken.substring(7);
        String mockUserId = mockSeed + "_mock_user_id";
        String mockNickname = mockSeed + "_mock_nickname";
        logger.info("* MOCK USER USED: " + mockUserId + " " + mockNickname);
        return new KarrotUserProfile(
                mockUserId,
                mockNickname
        );
    }
}
