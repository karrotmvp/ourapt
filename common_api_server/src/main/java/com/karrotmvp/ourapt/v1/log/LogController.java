package com.karrotmvp.ourapt.v1.log;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/log")
@Api(tags = "7. 로그 기록용")
@AllArgsConstructor
public class LogController {

  private final LogService logService;


  @GetMapping(value = "/first-request")
  @ApiOperation(value = "앱 방문시 첫 요청")
  public CommonResponseBody<Void> logFirstRequest(
    @RequestParam(name = "referer", required = false) Referer referer,
    @CurrentUser KarrotProfile profile
  ) {
    this.logService.logFirstRequest(referer, profile.getId());
    return CommonResponseBody.<Void>builder().success().build();
  }
}
