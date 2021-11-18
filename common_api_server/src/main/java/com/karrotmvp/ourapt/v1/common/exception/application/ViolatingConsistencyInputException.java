package com.karrotmvp.ourapt.v1.common.exception.application;

public class ViolatingConsistencyInputException extends RuntimeException {
  public ViolatingConsistencyInputException(String message) {
    super(message);
  }
}
