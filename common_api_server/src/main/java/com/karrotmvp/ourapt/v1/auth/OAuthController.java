package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotAccessTokenDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotLoginDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotOAuthResponseDto;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotOpenApiUserDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    public OAuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }


    @PostMapping("/karrot")
    @ApiOperation(value = "당근마켓 OAuth 로그인")
    @Transactional
    public CommonResponseBody<KarrotAccessTokenDto> karrotLogin(@RequestBody KarrotLoginDto body) {

        // accessToken 받아오기
        KarrotOAuthResponseDto responseDto = authService.getKarrotAccessToken(body.getAuthorizationCode());
        KarrotAccessTokenDto accessToken = new KarrotAccessTokenDto(responseDto);

        // accessToken 으로 유저데이터 받아서 저장
        KarrotOpenApiUserDto userProfile = authService
                .asyncGetUserProfileFromKarrot("Bearer " + accessToken.getAccessToken());

        User alreadySignedUpUser = userRepository.findById(userProfile.getUserId()).orElse(null);
        if (alreadySignedUpUser != null) {
            return CommonResponseBody.<KarrotAccessTokenDto>builder()
                    .success()
                    .data(accessToken)
                    .build();
        }

        User newUser = userProfile.toEntity();
        userRepository.save(newUser);

        return CommonResponseBody.<KarrotAccessTokenDto>builder()
                .success()
                .data(accessToken)
                .build();
    }


}
