package com.karrotmvp.ourapt.v1.common.karrotoapi;

import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.karrotapidto.KarrotOApiUserListResponseDto;
import com.karrotmvp.ourapt.v1.user.karrotapidto.KarrotOApiUserResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Component
public class KarrotOAPI {

    private final WebClient karrotOApiClient;

    public KarrotOAPI(@Qualifier("karrotOApiClient") WebClient karrotOApiClient) {
        this.karrotOApiClient = karrotOApiClient;
    }

    public List<KarrotProfile> getKarrotUserProfilesByIds(Set<String> ids) {
        KarrotOApiResponseBody<KarrotOApiUserListResponseDto> responseBody = this.sendGet("/api/v2/users/by_ids?ids=" + String.join(",", ids))
                .bodyToMono(new ParameterizedTypeReference<KarrotOApiResponseBody<KarrotOApiUserListResponseDto>>() {})
                .blockOptional().orElseThrow(KarrotUnexpectedResponseException::new);
        responseBody.checkIfError();
        return responseBody.getData().getUsers();
    }

    public KarrotProfile getKarrotUserProfileById(@NotNull String id) {
        KarrotOApiResponseBody<KarrotOApiUserResponseDto> responseBody = this.sendGet("/api/v2/users/" + id)
                .bodyToMono(new ParameterizedTypeReference<KarrotOApiResponseBody<KarrotOApiUserResponseDto>>() {})
                .blockOptional().orElseThrow(KarrotUnexpectedResponseException::new);
        responseBody.checkIfError();
        return responseBody.getData().getUser();
    }

    private WebClient.ResponseSpec sendGet(String uri) {
        // Generic 으로 Deserialize 하는것 아무리해도 안되서 포기...
        return this.karrotOApiClient
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), (resp) -> {
                    throw new KarrotUnexpectedResponseException("Get the " + resp.statusCode()  + " as response of KARROT OAPI");
                });
    }
}

