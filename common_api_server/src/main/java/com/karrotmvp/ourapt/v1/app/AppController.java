package com.karrotmvp.ourapt.v1.app;

import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.Static;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value = "/api/v1/app")
@Api(tags = "서버 애플리케이션 공통 API")
public class AppController {

  @GetMapping("/health-check")
  @ApiOperation(value = "서버 HealthCheck")
  public CommonResponseBody<Void> healthCheck() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hhmmss");
    return CommonResponseBody.<Void> builder()
        .success()
        .devMessage("I_AM_ALIVE: version_" + formatter.format(Static.serverStartTime))
        .build();
  }

  @GetMapping("/docs")
  @ApiIgnore
  public RedirectView redirectToSwaggerUi() {
    RedirectView targetView = new RedirectView();
    targetView.setUrl("/swagger-ui/index.html");
    return targetView;
  }
}