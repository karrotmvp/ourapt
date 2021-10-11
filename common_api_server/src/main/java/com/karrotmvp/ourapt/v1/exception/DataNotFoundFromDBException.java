package com.karrotmvp.ourapt.v1.exception;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class DataNotFoundFromDBException extends RequestFailException {


  public DataNotFoundFromDBException(String devMessage, String displayMessage) {
    super(devMessage, displayMessage);
  }

  @Override
  public ApiResult getApiResult() {
    // TODO Auto-generated method stub
    return ApiResult.DATA_NOT_FOUND_FROM_DB;
  }
  
}
