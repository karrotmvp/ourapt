package com.karrotmvp.ourapt.v1.common;

import lombok.Getter;

import java.util.Date;

@Getter
public class KarrotResponseBody<T> {

    private int status;
    private String message;
    private T data;
    private Date timestamp;
}
