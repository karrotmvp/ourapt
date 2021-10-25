package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.article.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuestionRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

//    @Test
//    void findByDateCursorWithPagingTest() {
//        List<Question> questions1 =
//                this.questionRepository.findByDateCursorWithPaging(
//                        new Date(),
//                        PageRequest.of(0, 10));
//        List<Question> questions2 =
//                this.questionRepository.findByDateCursorWithPaging(
//                        questions1.get(questions1.size() - 1).getCreatedAt(),
//                        PageRequest.of(0, 10));
//
//        assertTrue(questions1.size() <= 10);
//        assertTrue(questions2.size() <= 10);
//        assertTrue(questions2.stream()
//                .allMatch(q2 ->
//                        questions1.stream().noneMatch(
//                                (q1) -> q1.getId().equals(q2.getId())
//                        )
//                ));
//    }

}
