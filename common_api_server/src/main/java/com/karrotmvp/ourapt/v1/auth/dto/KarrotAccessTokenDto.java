package com.karrotmvp.ourapt.v1.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KarrotAccessTokenDto {
    private String accessToken;
    private String tokenType;
    private int expiredIn;
    private String scope;

    public KarrotAccessTokenDto(KarrotOAuthResponseDto oAuthResponse) {
        this.accessToken = oAuthResponse.getAccessToken();
        this.tokenType = oAuthResponse.getTokenType();
        this.expiredIn = oAuthResponse.getExpiredIn();
        this.scope = oAuthResponse.getScope();
    }
}
