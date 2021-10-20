package com.karrotmvp.ourapt.v1.app;

import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1/app")
public class AppController {

  @GetMapping("/health-check")
  @ApiOperation(value = "서버 HealthCheck")
  public CommonResponseBody<Void> healthCheck() {
    return CommonResponseBody.<Void> builder()
        .success()
        .devMessage("I_AM_ALIVE: version_2021_10_14_1522")
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