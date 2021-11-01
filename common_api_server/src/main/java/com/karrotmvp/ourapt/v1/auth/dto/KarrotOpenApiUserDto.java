package com.karrotmvp.ourapt.v1.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KarrotOpenApiUserDto {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("nickname")
    private String nickname;
}
