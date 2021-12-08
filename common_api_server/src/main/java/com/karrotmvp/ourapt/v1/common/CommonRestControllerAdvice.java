package com.karrotmvp.ourapt.v1.common;

import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotAuthenticationToken;
import com.karrotmvp.ourapt.v1.common.exception.application.AbstractWebApplicationContextException;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import com.karrotmvp.ourapt.v1.common.exception.application.RequestHeaderNotFoundException;
import com.karrotmvp.ourapt.v1.common.exception.application.ViolatingConsistencyInputException;
import com.karrotmvp.ourapt.v1.common.exception.event.ExceptionOccurEventPublisher;
import com.karrotmvp.ourapt.v1.log.event.AccessEventPublisher;
import com.karrotmvp.ourapt.v1.log.vo.LogVo;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestControllerAdvice
@AllArgsConstructor
public class CommonRestControllerAdvice {

  private final ExceptionOccurEventPublisher exceptionEventPublisher;
  private final AccessEventPublisher accessEventPublisher;

  // 일단 정상 응답 성공 시 Http status 는 모두 200으로, API 수행 성공여부는 responseBody 에서 다루는 형태.
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(AbstractWebApplicationContextException.class)
  public CommonResponseBody<Void> handleWebApplicationContextException(
    HttpServletRequest request,
    AbstractWebApplicationContextException exception
  ) {
    this.doLog(request, HttpStatus.OK, exception);
    return exception.toCommonResponseBody();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
    HttpMessageNotReadableException.class,
    HttpMediaTypeNotSupportedException.class,
    MethodArgumentNotValidException.class,
    RequestHeaderNotFoundException.class,
    ViolatingConsistencyInputException.class,
    MissingServletRequestParameterException.class
  })
  public CommonResponseBody<Void> handleHttpMessageNotReadableException(
    HttpServletRequest request,
    Exception exception
  ) {
    this.doLog(request, HttpStatus.BAD_REQUEST, exception);
    return CommonResponseBody.<Void>builder()
      .devMessage(exception.getMessage())
      .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({Exception.class, RuntimeException.class, KarrotUnexpectedResponseException.class})
  public CommonResponseBody<Void> handleUnexpectedException(
    HttpServletRequest request,
    Exception exception
  ) {
    this.doLog(request, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    return CommonResponseBody.<Void>builder()
      .devMessage("서버 문제로 인해 요청 실패")
      .displayMessage("알 수 없는 오류 입니다.")
      .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({UnsupportedOperationException.class})
  public CommonResponseBody<Void> handleUnsupportedOperationException(
    HttpServletRequest request,
    UnsupportedOperationException exception
  ) {
    this.doLog(request, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    return CommonResponseBody.<Void>builder()
      .devMessage("아직 구현되지 않음")
      .build();
  }

  private void doLog(HttpServletRequest request, HttpStatus status, Exception exception) {
    Authentication auth = getAuthenticationFromSecurity();
    accessEventPublisher.publish(
      LogVo.builder()
        .path(request.getRequestURI())
        .method(request.getMethod())
        .status(status.value())
        .regionId(request.getHeader("Region-Id"))
        .userId(parseUserId(auth).orElse(null))
        .build()
    );
    exceptionEventPublisher.publish(
      request.getMethod() + " " + request.getRequestURI() + " " + status.value(),
      exception.getMessage()
    );
  }

  private Authentication getAuthenticationFromSecurity() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  private Optional<String> parseUserId(Authentication auth) {
    if (!KarrotAuthenticationToken.class.isAssignableFrom(auth.getClass())) {
      return Optional.empty();
    }
    return Optional.of(((KarrotProfile) auth.getPrincipal()).getId());
  }
}
