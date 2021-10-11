package com.karrotmvp.ourapt.v1.common.exception.security;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import com.karrotmvp.ourapt.v1.common.exception.application.AbstractWebApplicationContextException;
import org.springframework.security.core.AuthenticationException;

public class KarrotInvalidAccessTokenException extends AuthenticationException {
    public KarrotInvalidAccessTokenException(String msg) {
        super(msg);
    }

    public KarrotInvalidAccessTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
