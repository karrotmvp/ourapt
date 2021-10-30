package com.karrotmvp.ourapt.v1.adminquestion;

import com.karrotmvp.ourapt.v1.adminquestion.dto.request.AnswerAdminQuestionDto;
import com.karrotmvp.ourapt.v1.adminquestion.dto.response.GetAvailableAdminQuestionDto;
import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "4. 관리자질문(탑피드)")
public class AdminQuestionController {

  @GetMapping(value = "/admin-question")
  @ApiOperation(value = "아파트에 해당하는 대답한 적 없는 AdminQuestion 가져오기")
  public CommonResponseBody<GetAvailableAdminQuestionDto> getAvailableAdminQuestion(
    @CurrentUser KarrotProfile profile,
    @RequestParam String apartmentId
  ) {
    throw new UnsupportedOperationException();
//    return CommonResponseBody.<GetAvailableAdminQuestionDto>builder()
//      .success()
//      .build();
  }

  @PostMapping(value = "/admin-question/{questionId}/answer")
  @ApiOperation(value = "AdminQuestion 에 대한 대답 제출")
  public CommonResponseBody<AnswerAdminQuestionDto> answerAdminQuestion(
    @CurrentUser KarrotProfile profile,
    @RequestBody AnswerAdminQuestionDto answerContent
  ) {
    throw new UnsupportedOperationException();
  }
}
