package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class DuplicatedRequestException extends AbstractWebApplicationContextException {

  public DuplicatedRequestException(String devMessage, String displayMessage) {
    super(devMessage, displayMessage);
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.RESOURCE_DUPLICATE;
  }

}
