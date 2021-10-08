package com.karrotmvp.ourapt.v1.exception;

import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

import lombok.Getter;

@Getter
public abstract class RequestFailException extends RuntimeException {
    private String devMessage = "";
    private String displayMessage = "";


    @Override
    public String getMessage() {
        return devMessage;
    }

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