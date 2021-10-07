package com.karrotmvp.ourapt.v1;

import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;

@RestController
@RequestMapping(value = "/v1/app")
public class AppController {

  @GetMapping("/health-check")
  public CommonResponseBody<Void> healthCheck() {
    return CommonResponseBody.<Void> builder()
        .result(ApiResult.SUCCESS)
        .devMessage("I_AM_ALIVE")
        .build();
  }

  @GetMapping("/api-doc")
  public RedirectView redirectToSwaggerUi() {
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl("/swagger-ui/index.html");
    return redirectView;
  }
  
}