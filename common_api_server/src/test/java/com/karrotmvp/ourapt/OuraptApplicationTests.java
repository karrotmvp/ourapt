package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.article.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class OuraptApplicationTests {


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    @Qualifier("karrotOApiClient")
    private WebClient karrotOApiClient;

    @Test
    void contextLoads() {
    }

//    @Test
//    void repositoryTest() {
//        List<Question> questions =
//                this.questionRepository.findByDateCursorWithPaging(new Date(), PageRequest.of(0, 10));
//        Map response = this.karrotOApiClient
//                .get()
//                .uri("/api/v2/users/by_ids" +
//                        String.join(",", questions.stream()
//                                .map(q -> q.getWriter().getKarrotId())
//                                .collect(Collectors.toList()))
//                )
//                .retrieve()
//                .onStatus(status -> !status.is2xxSuccessful(), (resp) -> {
//                    throw new RuntimeException(resp.statusCode() + "got.");
//                })
//                .bodyToMono(Map.class)
//                .blockOptional()
//                .orElse(null);
//
//
//        questions.forEach((q) -> {
//            System.out.println(q.getWriter().getKarrotId());
//        });
//    }


}
