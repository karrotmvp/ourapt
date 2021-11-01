package com.karrotmvp.ourapt.v1.common.constant;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel
public enum ApiResult {
    SUCCESS("SUCCESS"),
    INVALID_INPUT("INVALID_INPUT"),
    RESOURCE_DUPLICATE("RESOURCE_DUPLICATE"),
    DATA_NOT_FOUND_FROM_DB("DATA_NOT_FOUND_FROM_DB"),
    KARROT_UNAUTHORIZED_CODE("KARROT_UNAUTHORIZED_CODE"),
    KARROT_INVALID_ACCESS_TOKEN("KARROT_INVALID_ACCESS_TOKEN"),
    REGISTERED_USER_NOT_FOUND("REGISTERED_USER_NOT_FOUND"),
    NOT_SERVICED_REGION("NOT_SERVICED_REGION");


    @Getter
    private final String result;

    ApiResult(String resultCode) {
        this.result = resultCode;
    }
}