package com.karrotmvp.ourapt.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserService {

    @Autowired
    @Qualifier("karrotOApiClient")
    private WebClient karrotOApiClient;

}