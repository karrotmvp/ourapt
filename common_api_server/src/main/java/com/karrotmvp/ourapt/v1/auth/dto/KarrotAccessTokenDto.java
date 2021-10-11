package com.karrotmvp.ourapt.v1.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.Date;

@Getter
public class KarrotAccessTokenDto {
    private String accessToken;
    private String tokenType;
    private Date expiredIn;
    private String scope;
}
