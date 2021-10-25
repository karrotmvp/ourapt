package com.karrotmvp.ourapt.v1.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KarrotOpenApiResponseBody<T> {

    @JsonProperty("status")
    private int status;
    private T data;
    @JsonProperty("timestamp")
    private String timestamp;
}
