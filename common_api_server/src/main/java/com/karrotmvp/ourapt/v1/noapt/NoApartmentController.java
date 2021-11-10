package com.karrotmvp.ourapt.v1.noapt;

import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/no-apartment")
@Api(tags = "99. 새로운 아파트 서비스 니즈")
public class NoApartmentController {

  public final NoApartmentRepository noApartmentRepository;

  public NoApartmentController(NoApartmentRepository noApartmentRepository) {
    this.noApartmentRepository = noApartmentRepository;
  }

  @PostMapping(value = "")
  @ApiOperation(value = "거주하는 아파트가 없나요? 답변")
  @Transactional
  public CommonResponseBody<Void> submitAnswerNoApartment(
    @RequestBody @Valid NoApartmentAnswerDto answer,
    @RequestHeader(name = "Instance-Id") String instanceId,
    @RequestHeader(name = "Region-Id") String regionId,
    @CurrentUser KarrotProfile profile
  ) {
    this.noApartmentRepository.save(new NoApartment(instanceId, answer.getAnswer(), regionId, profile.getId()));
    return CommonResponseBody.<Void>builder()
      .success().build();
  }


}
