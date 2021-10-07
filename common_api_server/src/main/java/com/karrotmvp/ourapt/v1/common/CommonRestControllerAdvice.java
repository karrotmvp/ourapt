package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.exception.RequestFailException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonRestControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 일단 정상 응답 성공 시 Http status 는 모두 200으로, API 수행 성공여부는 responseBody 에서 다루는 형태.
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RequestFailException.class)
    public CommonResponseBody<Void> handleRequestFailException(RequestFailException exception) {
        logger.info(exception.getDevMessage());
        logger.info(exception.getMessage());
        return exception.toCommonResponseBody();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public CommonResponseBody<Void> handleUnexpectedException(Exception exception) {
        logger.error(exception.getMessage());
        return CommonResponseBody.<Void>builder()
                .devMessage("서버 문제로 인해 요청 실패")
                .displayMessage("알 수 없는 오류 입니다.")
                .build();
    }
}
