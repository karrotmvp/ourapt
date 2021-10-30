package com.karrotmvp.ourapt.v1.adminquestion;

import com.karrotmvp.ourapt.v1.adminquestion.dto.response.GetAvailableAdminQuestionDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.dto.KarrotOpenApiUserDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin-question")
public class AdminQuestionController {

  // TODO: implement
  @GetMapping(value = "")
  @ApiOperation(value = "리전에 포함된, 대답한 적 없는 AdminQuestion 가져오기")
  public CommonResponseBody<GetAvailableAdminQuestionDto> getAvailableAdminQuestion(
    @CurrentUser KarrotOpenApiUserDto profile
  ) {
    return CommonResponseBody.<GetAvailableAdminQuestionDto>builder()
      .success()
      .build();
  }
}
