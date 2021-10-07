package com.karrotmvp.ourapt.v1.preopen;

import com.karrotmvp.ourapt.v1.auth.KarrotAuthenticationToken;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import com.karrotmvp.ourapt.v1.exception.DuplicatedRequestException;
import com.karrotmvp.ourapt.v1.preopen.dto.PreopenFormDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/preopen")
public class PreopenController {

  // @Autowired
  // private UserService userService;

  @Autowired
  private PreopenRepository preopenRepository;

  @PostMapping("/join")
  public CommonResponseBody<Void> voteToJoin(@RequestBody PreopenFormDto dto, KarrotAuthenticationToken authentication) {
    PreopenForm preOpenForm = dto.toEntity();

    // Check duplication
    PreopenForm duplicatedData = preopenRepository.findById(preOpenForm.getKarrotId()).orElse(null);;
    if (duplicatedData != null) {
      throw new DuplicatedRequestException("이미 알림톡 신청한 유저. 데이터 이미 있음.", "이미 알림톡을 신청하셨어요! 서비스가 오픈하면 알림톡으로 알려드릴게요!");
    }

    preOpenForm.setUser(authentication.getPrincipal().toEntity());

    preopenRepository.save(preOpenForm);


    return CommonResponseBody.<Void>builder()
        .result(ApiResult.SUCCESS)
        .build();
  }

}