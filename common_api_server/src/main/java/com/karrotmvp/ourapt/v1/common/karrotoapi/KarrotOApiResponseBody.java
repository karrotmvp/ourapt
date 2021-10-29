package com.karrotmvp.ourapt.v1.common.karrotoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

//@NoArgsConstructor
@Getter
@Setter
public class KarrotOApiResponseBody<T> {

    private List<KarrotOApiError> errors;
    private T data; // JSON

    public void checkIfError() {
        if (this.getErrors() != null) {
            throw new KarrotUnexpectedResponseException(this.getErrors()
                    .stream()
                    .map(KarrotOApiError::getMessage)
                    .collect(Collectors.joining(", ")));
        }
    }

    @JsonCreator
    public KarrotOApiResponseBody(@JsonProperty("data") T data) {
        this.data = data;
    }
}
