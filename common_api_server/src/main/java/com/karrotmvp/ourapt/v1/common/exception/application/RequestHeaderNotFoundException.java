package com.karrotmvp.ourapt.v1.common.exception.application;

public class RequestHeaderNotFoundException extends RuntimeException {

  public RequestHeaderNotFoundException(String message) {
    super(message);
  }
}
