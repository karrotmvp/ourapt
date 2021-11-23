package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.article.vote.VoteService;
import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;
import com.karrotmvp.ourapt.v1.article.vote.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OuraptApplicationTests {

    @Autowired
    VoteService voteService;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void contextLoads() {
//        List<Vote> votes = this.voteService.getRandomPinnedOfApartment("TEST");
        List<Vote> vote = this.voteRepository.findByApartmentIdAndPinned("TEST");
        vote.forEach(v -> v.getItems().forEach(i -> System.out.println(i.getVoterIds())));
    }
}
