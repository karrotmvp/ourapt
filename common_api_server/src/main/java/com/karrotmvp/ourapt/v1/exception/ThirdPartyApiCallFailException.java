package com.karrotmvp.ourapt.v1.exception;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class ThirdPartyApiCallFailException extends RequestFailException{

  public ThirdPartyApiCallFailException(String devMessage, String displayMessage) {
    super(devMessage, displayMessage);
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.DATA_NOT_FOUND_FROM_DB; // TODO: change to proper ApiResult
  }
  
}
