package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class BannedUserException extends AbstractWebApplicationContextException {
  public BannedUserException() {
    super("관리자에 의해 차단된 이용자입니다.", "");
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.BANNED_USER;
  }
}