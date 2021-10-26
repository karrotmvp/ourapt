package com.karrotmvp.ourapt.v1.app;

import java.text.SimpleDateFormat;

import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1/app")
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