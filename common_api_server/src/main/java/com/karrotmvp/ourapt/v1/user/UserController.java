package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
import com.karrotmvp.ourapt.v1.user.dto.request.ChangeMyCheckedInDto;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
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
      .data(this.userService.getUserById(profile.getId()))
      .build();
  }

  @PatchMapping("/me/checkedIn")
  @ApiOperation("자신의 아파트 체크인 정보 업데이트")
  public CommonResponseBody<Void> changeMyCheckedIn(
    @CurrentUser KarrotProfile profile,
    @RequestBody ChangeMyCheckedInDto newCheckedInInfo
  ) {
    this.userService.updateCheckedInUserById(profile.getId(), newCheckedInInfo.getNewApartmentId());
    return CommonResponseBody.<Void>builder()
      .success()
      .build();
  }
}
