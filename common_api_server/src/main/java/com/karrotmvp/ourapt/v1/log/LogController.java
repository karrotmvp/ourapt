package com.karrotmvp.ourapt.v1.log;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/log")
@Api(tags = "7. 로그 기록용")
public class LogController {

  private final FirstRequestLogRepository firstRequestLogRepository;

  public LogController(FirstRequestLogRepository firstRequestLogRepository) {
    this.firstRequestLogRepository = firstRequestLogRepository;
  }

  @GetMapping(value = "/first-request")
  @ApiOperation(value = "앱 방문시 첫 요청")
  @Transactional
  public CommonResponseBody<Void> logFirstRequest(
    @RequestParam(name = "referer", required = false) Referer referer,
    @CurrentUser KarrotProfile karrotProfile
  ) {
    FirstRequestLog firstLog = this.firstRequestLogRepository.findById(karrotProfile.getId()).orElse(null);
    if (firstLog != null) {
        return CommonResponseBody.<Void>builder().success().build();
    }
    this.firstRequestLogRepository.save(new FirstRequestLog(karrotProfile.getId(), referer != null ? referer : Referer.UNKNOWN));
    return CommonResponseBody.<Void>builder().success().build();
  }
}
