package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotAccessTokenDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotOAuthResponseDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotLoginDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "당근마켓 OAuth 로그인")
    public CommonResponseBody<KarrotAccessTokenDto> karrotLogin(@RequestBody KarrotLoginDto body) {
        KarrotOAuthResponseDto responseDto = authService.getKarrotAccessToken(body.getAuthorizationCode());
        KarrotAccessTokenDto accessToken = new KarrotAccessTokenDto(responseDto);
        // accessToken 받아오기
        return CommonResponseBody.<KarrotAccessTokenDto>builder()
                .success()
                .data(accessToken)
                .build();
    }


}
