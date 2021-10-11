package com.karrotmvp.ourapt.v1.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KarrotResponseBody<T> {

    @JsonProperty("status")
    private int status;
    private T data;
    @JsonProperty("timestamp")
    private String timestamp;
}
