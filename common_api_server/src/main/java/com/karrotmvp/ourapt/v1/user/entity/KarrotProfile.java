package com.karrotmvp.ourapt.v1.user.entity;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class KarrotProfile {
    @NotNull
    private String id;
    @NotNull
    private String nickname;
    private String profileImageUrl;
}
