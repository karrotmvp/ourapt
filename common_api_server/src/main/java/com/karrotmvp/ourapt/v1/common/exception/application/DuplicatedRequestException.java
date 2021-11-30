package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class DuplicatedRequestException extends AbstractWebApplicationContextException {

  public DuplicatedRequestException(String devMessage) {
    super(devMessage, "");
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.RESOURCE_DUPLICATE;
  }

}
