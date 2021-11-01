package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.dto.UserDto;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/v1/user")
@Api(tags = "6. 사용자")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @ApiOperation("자신의 정보 조회")
  public CommonResponseBody<UserDto> getMyInfo(
    @CurrentUser KarrotProfile profile
  ) {
    return CommonResponseBody.<UserDto>builder()
      .success()
      .data(this.userService.getUserByUserId(profile.getId()))
      .build();
  }
}
