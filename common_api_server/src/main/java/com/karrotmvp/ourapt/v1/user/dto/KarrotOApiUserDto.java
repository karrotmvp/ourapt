package com.karrotmvp.ourapt.v1.user.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class KarrotOApiUserDto {
    private String id;
    private String nickname;
    private String profileImageUrl;
}