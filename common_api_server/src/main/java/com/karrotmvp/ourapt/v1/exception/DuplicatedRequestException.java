package com.karrotmvp.ourapt.v1.exception;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class DuplicatedRequestException extends RequestFailException {

  public DuplicatedRequestException(String devMessage, String displayMessage) {
    super(devMessage, displayMessage);
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.RESOURCE_DUPLICATE;
  }
  
}
