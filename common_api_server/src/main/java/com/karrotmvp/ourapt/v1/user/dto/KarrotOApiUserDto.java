package com.karrotmvp.ourapt.v1.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class KarrotOApiUserDto {
    private String id;
    private String nickname;
    private String profileImageUrl;
}