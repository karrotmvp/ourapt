package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import com.karrotmvp.ourapt.v1.common.exception.application.AbstractWebApplicationContextException;
import org.springframework.security.core.AuthenticationException;

public class KarrotUnexpectedResponseException extends RuntimeException {
    public KarrotUnexpectedResponseException(String msg, Throwable cause) {
        super("[서버개발자에게 문의 필요]" + msg, cause);
    }

    public KarrotUnexpectedResponseException(String msg) {
        super(msg + "[서버개발자에게 문의 필요]" + msg);
    }
}
