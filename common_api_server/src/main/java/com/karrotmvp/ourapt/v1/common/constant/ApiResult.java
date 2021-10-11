package com.karrotmvp.ourapt.v1.common.constant;

import lombok.Getter;

public enum ApiResult {
    SUCCESS("SUCCESS"),
    INVALID_INPUT("INVALID_INPUT"),
    RESOURCE_DUPLICATE("RESOURCE_DUPLICATE"),
    DATA_NOT_FOUND_FROM_DB("DATA_NOT_FOUND_FROM_DB"),
    UPSTREAM_API_CALL_FAIL("UPSTREAM_API_CALL_FAIL");

    @Getter
    private String result;

    ApiResult(String resultCode) {
        this.result = resultCode;
    }
}