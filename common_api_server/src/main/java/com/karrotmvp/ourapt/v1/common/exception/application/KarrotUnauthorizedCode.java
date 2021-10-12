package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import org.springframework.security.core.AuthenticationException;

public class KarrotUnauthorizedCode extends AbstractWebApplicationContextException {
    public KarrotUnauthorizedCode(String devMessage, String displayMessage) {
        super(devMessage, displayMessage);
    }

    @Override
    public ApiResult getApiResult() {
        return null;
    }
}