package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotUserProfile;
import com.karrotmvp.ourapt.v1.common.exception.application.KarrotUnexpectedResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("karrotOApiClient")
    private WebClient karrotOApiClient;

    public KarrotUserProfile getKarrotUserProfilesByIds(List<String> ids) {
        Map response = this.karrotOApiClient
                .get()
                .uri("/api/v2/users/by_ids" + String.join(",", ids))
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), (resp) -> {
                    throw new KarrotUnexpectedResponseException("got " + resp.statusCode() + "from karrot-opai");
                })
                .bodyToMono(Map.class)
                .blockOptional()
                .orElse(null);
//    if (response.get("errors") != null) {
//
//    }
        return null;
    }



}