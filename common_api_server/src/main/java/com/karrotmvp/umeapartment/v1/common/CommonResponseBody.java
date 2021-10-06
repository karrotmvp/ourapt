package com.karrotmvp.umeapartment.v1.common;

import java.io.Serializable;

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

  private T data = null;

  public static class CommonResponseBodyBuilder<T> {
    private String status;
    public CommonResponseBodyBuilder<T> result(ApiResult result) {
      this.status = result.getResult();
      return this;
    }
  }
}
