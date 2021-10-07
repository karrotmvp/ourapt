package com.karrotmvp.ourapt.v1;

import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v1/app")
public class AppController {

  @GetMapping("/health-check")
  public CommonResponseBody<Void> healthCheck() {
    return CommonResponseBody.<Void> builder()
        .result(ApiResult.SUCCESS)
        .build();
  }
  
}