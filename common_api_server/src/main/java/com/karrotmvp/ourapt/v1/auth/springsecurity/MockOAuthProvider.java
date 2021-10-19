package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile({"!production"})
public class MockOAuthProvider extends KarrotOAuthProvider {
    // MockOAuthProvider is a Bean that is injected instead of KarrotOAuthProvider when the Profile is not production

    public MockOAuthProvider(WebClient webClient, KarrotProperty karrotProperty, Environment env) {
        super(webClient, karrotProperty, env);
    }

    @Override
    public KarrotUserProfile asyncGetUserProfileFromKarrot(String accessToken) {
        String mockSeed = accessToken.substring(7);
        return new KarrotUserProfile(
                mockSeed + "_mock_user_id",
                mockSeed + "_mock_nickname"
        );
    }
}
