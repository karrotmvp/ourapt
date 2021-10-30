package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import org.springframework.security.core.AuthenticationException;

public class KarrotUnauthorizedCodeException extends AbstractWebApplicationContextException {
    public KarrotUnauthorizedCodeException(String devMessage, String displayMessage) {
        super(devMessage, displayMessage);
    }

    @Override
    public ApiResult getApiResult() {
        return ApiResult.KARROT_UNAUTHORIZED_CODE;
    }
}
