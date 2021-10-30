package com.karrotmvp.ourapt.v1.common.exception.application;

public class NotFoundInRegionDictException extends RuntimeException{


  private static final String prefix = "[서버개발자에게 문의 필요] ";

  public NotFoundInRegionDictException(String regionId) {
    super(prefix + "입력된 regionId가 설정된 RegionDict에 없음 -" + regionId);
  }
  public NotFoundInRegionDictException(String msg, Throwable cause) {
    super(prefix + msg, cause);
  }

}