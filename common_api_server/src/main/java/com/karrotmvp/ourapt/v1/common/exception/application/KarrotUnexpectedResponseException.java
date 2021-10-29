package com.karrotmvp.ourapt.v1.common.exception.application;

public class KarrotUnexpectedResponseException extends RuntimeException {

    private static String prefix = "[서버개발자에게 문의 필요] ";

    public KarrotUnexpectedResponseException() {
        super(prefix + "정상적이지 않은 KarrotOAPI 응답입니다");
    }

    public KarrotUnexpectedResponseException(String msg) {
        super(prefix + msg);
    }

    public KarrotUnexpectedResponseException(String msg, Throwable cause) {
        super(prefix + msg, cause);
    }


}
