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

  private String status;

  @Builder.Default
  private String devMessage = "";

  @Builder.Default
  private String displayMessage = "";

  @Builder.Default
  private T data = null;


  public static class CommonResponseBodyBuilder<T> {
    private String status;

    public CommonResponseBodyBuilder<T> success() {
      this.status = ApiResult.SUCCESS.getResult();
      return this;
    }

    public CommonResponseBodyBuilder<T> result(ApiResult result) {
      this.status = result.getResult();
      return this;
    }
  }
}
