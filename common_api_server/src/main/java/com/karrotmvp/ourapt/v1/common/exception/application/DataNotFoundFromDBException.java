package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class DataNotFoundFromDBException extends AbstractWebApplicationContextException {


  public DataNotFoundFromDBException(String message) {
    super(message, "");
  }
  public DataNotFoundFromDBException(String devMessage, String displayMessage) {
    super(devMessage, displayMessage);
  }

  @Override
  public ApiResult getApiResult() {
    // TODO Auto-generated method stub
    return ApiResult.DATA_NOT_FOUND_FROM_DB;
  }
  
}
