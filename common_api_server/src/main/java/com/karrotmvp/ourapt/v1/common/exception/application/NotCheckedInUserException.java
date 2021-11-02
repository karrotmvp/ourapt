package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class NotCheckedInUserException extends AbstractWebApplicationContextException{

  public NotCheckedInUserException() {
    super("아직 유저가 체크인된 아파트가 없습니다.", "");
  }

  @Override
  public ApiResult getApiResult() {
    return null;
  }
}
