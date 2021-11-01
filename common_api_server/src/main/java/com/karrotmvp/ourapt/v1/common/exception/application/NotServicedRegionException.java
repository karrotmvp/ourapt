package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class NotServicedRegionException extends AbstractWebApplicationContextException{

  public NotServicedRegionException() {
      super("아직 지원되지 않는 지역입니다.", "");
  }

  @Override
  public ApiResult getApiResult() {
    return ApiResult.NOT_SERVICED_REGION;
  }
}
