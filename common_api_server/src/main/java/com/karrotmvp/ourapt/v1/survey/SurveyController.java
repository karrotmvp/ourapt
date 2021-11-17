package com.karrotmvp.ourapt.v1.survey;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@RestController
@RequestMapping(value = "/api/v1/survey")
@Api(tags = "설문용 임시 controller")
public class SurveyController {

  @Autowired
  private EntityManager em;

  @PostMapping("")
  @Transactional
  @ApiOperation("[임시] 설문조사 제출")
  public CommonResponseBody<Void> submitSurvey(
    @RequestBody SurveyDto form,
    @CurrentUser KarrotProfile profile
  ) {
    Survey s = new Survey();
    s.setUserId(profile.getId());
    s.setContentJson(form.getContentJson());
    em.persist(s);
    return CommonResponseBody.<Void>builder()
      .success()
      .build();
  }

}
