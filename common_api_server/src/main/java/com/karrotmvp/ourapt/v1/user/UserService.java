package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.common.dto.KarrotOApiError;
import com.karrotmvp.ourapt.v1.common.dto.KarrotOApiResponseBody;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import com.karrotmvp.ourapt.v1.user.dto.KarrotOApiUserDto;
import com.karrotmvp.ourapt.v1.user.dto.KarrotOApiUserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("karrotOApiClient")
    private WebClient karrotOApiClient;

    public List<KarrotOApiUserDto> getKarrotUserProfilesByIds(Set<String> ids) {
        KarrotOApiResponseBody<KarrotOApiUserListDto> responseBody = this.karrotOApiClient
                .get()
                .uri("/api/v2/users/by_ids?ids=" + String.join(",", ids))
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), (resp) -> {
                    throw new KarrotUnexpectedResponseException("KARROT OAPI 호출 중에 " + resp.statusCode() + "응답을 받았습니다.");
                })
                .bodyToMono(new ParameterizedTypeReference<KarrotOApiResponseBody<KarrotOApiUserListDto>>() {})
                .blockOptional()
                .orElseThrow(() -> new KarrotUnexpectedResponseException("정상적이지 않은 KarrotOAPI 응답입니다"));
        if (responseBody.getErrors() != null) {
            throw new KarrotUnexpectedResponseException(responseBody.getErrors().stream()
                    .map(KarrotOApiError::getMessage)
                    .collect(Collectors.joining(", ")));
        }
        return responseBody.getData().getUsers();
    }



}