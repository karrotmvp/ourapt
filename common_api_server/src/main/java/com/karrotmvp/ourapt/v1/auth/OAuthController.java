package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotAccessTokenDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotLoginDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/karrot")
    public CommonResponseBody<KarrotAccessTokenDto> karrotLogin(@RequestBody KarrotLoginDto body) {
        KarrotAccessTokenDto accessToken = authService.getKarrotAccessToken(body.getAuthorizationCode());
        return CommonResponseBody.<KarrotAccessTokenDto>builder()
                .success()
                .data(accessToken)
                .build();
    }
}
