package com.karrotmvp.ourapt.v1.user.entity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class KarrotProfile {
    private String id;
    private String nickname;
    private String profileImageUrl;
}
