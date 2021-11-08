package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.auth.dto.KarrotAccessTokenDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotLoginDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotOAuthResponseDto;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotOpenApiUserDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/oauth")
@Api(tags = "3. 당근마켓 연동 인증")
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
  public CommonResponseBody<KarrotAccessTokenDto> karrotLogin(
    @RequestBody KarrotLoginDto body,
    @RequestHeader(name = "Instance-Id") String instanceId
  ) {

    // accessToken 받아오기
    KarrotOAuthResponseDto responseDto = authService.getKarrotAccessToken(body.getAuthorizationCode());
    KarrotAccessTokenDto accessToken = new KarrotAccessTokenDto(responseDto);

    // accessToken 으로 유저데이터 받아서 저장
    KarrotOpenApiUserDto userProfile = authService
      .asyncGetUserProfileFromKarrot("Bearer " + accessToken.getAccessToken());

    User alreadySignedUpUser = userRepository.findById(userProfile.getUserId()).orElse(null);
    if (alreadySignedUpUser != null) {
      // login
      // instanceId 업데이트
      alreadySignedUpUser.setInstanceId(instanceId);
      userRepository.save(alreadySignedUpUser);
      return CommonResponseBody.<KarrotAccessTokenDto>builder()
        .success()
        .data(accessToken)
        .build();
    }

    //signup
    User newUser = new User(userProfile.getUserId(), new KarrotProfile(userProfile.getUserId(), userProfile.getNickname(), ""), false);
    newUser.setPushAgreedAt(new Date());
    newUser.setInstanceId(instanceId);
    userRepository.save(newUser);

    return CommonResponseBody.<KarrotAccessTokenDto>builder()
      .success()
      .data(accessToken)
      .build();
  }


}
