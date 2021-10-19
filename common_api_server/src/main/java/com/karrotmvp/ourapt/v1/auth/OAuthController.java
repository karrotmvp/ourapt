package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotAccessTokenDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotOAuthResponseDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotLoginDto;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotUserProfile;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.User;
import com.karrotmvp.ourapt.v1.user.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/karrot")
    @ApiOperation(value = "당근마켓 OAuth 로그인")
    @Transactional
    public CommonResponseBody<KarrotAccessTokenDto> karrotLogin(@RequestBody KarrotLoginDto body) {

        // accessToken 받아오기
        KarrotOAuthResponseDto responseDto = authService.getKarrotAccessToken(body.getAuthorizationCode());
        KarrotAccessTokenDto accessToken = new KarrotAccessTokenDto(responseDto);

        // accessToken 으로 유저데이터 받아서 저장
        KarrotUserProfile userProfile = authService
                .asyncGetUserProfileFromKarrot("Bearer " + accessToken.getAccessToken());

        User alreadySignedUpUser = userRepository.findById(userProfile.getUserId()).orElse(null);
        if (alreadySignedUpUser != null) {
            return CommonResponseBody.<KarrotAccessTokenDto>builder()
                    .success()
                    .data(accessToken)
                    .build();
        }

        User newUser = userProfile.toEntity();
        newUser.setPushAgreedAt(new Date());

        userRepository.save(userProfile.toEntity());

        return CommonResponseBody.<KarrotAccessTokenDto>builder()
                .success()
                .data(accessToken)
                .build();
    }


}
