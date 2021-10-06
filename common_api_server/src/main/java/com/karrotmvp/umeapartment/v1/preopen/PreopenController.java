package com.karrotmvp.umeapartment.v1.preopen;

import com.karrotmvp.umeapartment.v1.auth.KarrotAuthenticationToken;
import com.karrotmvp.umeapartment.v1.common.ApiResult;
import com.karrotmvp.umeapartment.v1.common.CommonResponseBody;
import com.karrotmvp.umeapartment.v1.user.User;
import com.karrotmvp.umeapartment.v1.user.UserProfileDto;
import com.karrotmvp.umeapartment.v1.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/preopen")
public class PreopenController {

  @Autowired
  private UserService userService;

  @Autowired
  private PreopenRepository preopenRepository;

  @PostMapping("/join")
  public CommonResponseBody<Void> voteToJoin(@RequestBody PreopenDto dto, KarrotAuthenticationToken authentication) {
    UserProfileDto profile = authentication.getPrincipal();
    User newUser = profile.toEntity();
    Preopen preOpenData = dto.toEntity();
    


    return CommonResponseBody.<Void>builder()
        .result(ApiResult.SUCCESS)
        .build();
  }

}