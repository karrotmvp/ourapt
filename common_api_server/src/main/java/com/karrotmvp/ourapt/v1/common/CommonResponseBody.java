package com.karrotmvp.ourapt.v1.common;

import java.io.Serializable;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CommonResponseBody<T> implements Serializable {

  private ApiResult status;

  @Builder.Default
  private String devMessage = "";

  @Builder.Default
  private String displayMessage = "";

  @Builder.Default
  private T data = null;


  public static class CommonResponseBodyBuilder<T> {
    public CommonResponseBodyBuilder<T> success() {
      this.status = ApiResult.SUCCESS;
      return this;
    }

    public CommonResponseBodyBuilder<T> result(ApiResult result) {
      this.status = result;
      return this;
    }
  }
}
