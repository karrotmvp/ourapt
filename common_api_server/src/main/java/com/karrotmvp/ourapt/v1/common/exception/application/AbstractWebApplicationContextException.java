package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

import lombok.Getter;

@Getter
public abstract class AbstractWebApplicationContextException extends RuntimeException {
    private String devMessage = "";
    private String displayMessage = "";


    @Override
    public String getMessage() {
        return devMessage;
    }

    public AbstractWebApplicationContextException(String devMessage, String displayMessage) {
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