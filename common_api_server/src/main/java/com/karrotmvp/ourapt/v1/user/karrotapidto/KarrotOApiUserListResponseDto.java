package com.karrotmvp.ourapt.v1.user.karrotapidto;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KarrotOApiUserListResponseDto {
    private List<KarrotProfile> users;
}
