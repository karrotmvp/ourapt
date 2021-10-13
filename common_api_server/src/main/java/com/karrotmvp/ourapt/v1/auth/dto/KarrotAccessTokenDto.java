package com.karrotmvp.ourapt.v1.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KarrotAccessTokenDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expired_in")
    private int expiredIn;
    @JsonProperty("scope")
    private String scope;
}
