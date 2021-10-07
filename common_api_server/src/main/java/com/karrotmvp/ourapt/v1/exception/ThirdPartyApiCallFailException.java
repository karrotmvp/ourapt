package com.karrotmvp.ourapt.v1.exception;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class ThirdPartyApiCallFailException extends RequestFailException{

  public ThirdPartyApiCallFailException(String devMessage, String displayMessage) {
    super(devMessage, displayMessage);
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.RESOURCE_NOT_FOUND; // TODO: change to proper ApiResult
  }
  
}
