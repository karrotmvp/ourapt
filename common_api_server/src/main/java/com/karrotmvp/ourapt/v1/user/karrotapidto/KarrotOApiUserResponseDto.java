package com.karrotmvp.ourapt.v1.user.karrotapidto;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class KarrotOApiUserResponseDto {
    private KarrotProfile user;
}
