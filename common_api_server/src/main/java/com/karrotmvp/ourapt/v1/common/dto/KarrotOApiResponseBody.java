package com.karrotmvp.ourapt.v1.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KarrotOApiResponseBody<T> {

    private List<KarrotOApiError> errors;
    private T data;
}
