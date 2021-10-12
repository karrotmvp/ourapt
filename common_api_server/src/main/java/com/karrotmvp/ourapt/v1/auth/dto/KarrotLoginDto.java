package com.karrotmvp.ourapt.v1.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KarrotLoginDto {
    private String authorizationCode;
}
