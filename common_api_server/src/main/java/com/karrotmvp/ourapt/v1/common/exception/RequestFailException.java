package com.karrotmvp.ourapt.v1.common.exception;

import com.karrotmvp.ourapt.v1.common.ApiResult;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;

import lombok.Getter;

@Getter
public abstract class RequestFailException extends RuntimeException {
    private String devMessage;
    private String displayMessage;


    public RequestFailException(String devMessage, String displayMessage) {
        this.devMessage = devMessage;
        this.displayMessage = displayMessage;
    };

    public CommonResponseBody<Void> toCommonResponseBody() {
        return CommonResponseBody.<Void>builder()
                .result(this.getApiResult())
                .devMessage(this.getDevMessage())
                .displayMessage(this.getDisplayMessage())
                .build();
    }

    abstract public ApiResult getApiResult();
}