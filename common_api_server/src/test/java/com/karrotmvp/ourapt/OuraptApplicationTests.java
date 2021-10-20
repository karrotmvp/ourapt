package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.common.property.KarrotProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@SpringBootTest
class OuraptApplicationTests {

    @Autowired
    @Qualifier("karrotOpenApiClient")
    private WebClient karrotOpenApiClient;

    @Autowired
    @Qualifier("karrotOApiClient")
    private WebClient karrotOApiClient;

    @Autowired
    private KarrotProperty karrotProperty;

    static class Body {
        static class Region {
			public String id;
            public String nodeId;
            public String name;
            public String name1;
            public String name1Id;
            public String name2;
            public String name2Id;
            public String name3;
            public String name3Id;
        }
        static class Data {
            public Map<String, String> region;
        }

        public Data data;
    }


    @Test
    void contextLoads() {
    }

    @Test
    void callApi() {
        Mono<ResponseEntity<Body>> res = karrotOApiClient
                .get()
                .uri("/api/v2/regions/" + "6530459d189b")
                .retrieve()
                .toEntity(Body.class);
    }

}
