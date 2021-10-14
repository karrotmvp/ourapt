package com.karrotmvp.ourapt.v1;

import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/app")
public class AppController {

  @GetMapping("/health-check")
  public CommonResponseBody<Void> healthCheck() {
    return CommonResponseBody.<Void> builder()
        .result(ApiResult.SUCCESS)
        .devMessage("I_AM_ALIVE: version_2021_10_14_1522")
        .build();
  }

  @GetMapping("/api-doc")
  public String redirectToSwaggerUi() {
    return "redirect::/swagger-ui/index.html";
  }

  @GetMapping("/token")
  public String tempToken(@RequestParam("token") String token) {
    System.out.println("token");
    return "asdfasdf";
  }
  
}