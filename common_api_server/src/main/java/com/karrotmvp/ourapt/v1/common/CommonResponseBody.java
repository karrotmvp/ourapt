package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Setter
@Getter
public class CommonResponseBody<T> implements Serializable {

  @NotNull
  private ApiResult status;

  @Builder.Default
  @NotNull
  private String devMessage = "";

  @Builder.Default
  @NotNull
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
