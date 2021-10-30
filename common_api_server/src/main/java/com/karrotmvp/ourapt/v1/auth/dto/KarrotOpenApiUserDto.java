package com.karrotmvp.ourapt.v1.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
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

    public User toEntity() {
        return new User(
                this.userId,
                new KarrotProfile(this.userId, this.userId, this.nickname)
        );
    }
}
