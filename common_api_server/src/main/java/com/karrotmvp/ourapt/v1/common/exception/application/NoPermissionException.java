package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class NoPermissionException extends AbstractWebApplicationContextException {

  public NoPermissionException(String devMessage) {
    super(devMessage, "");
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.NO_PERMISSION;
  }
}
