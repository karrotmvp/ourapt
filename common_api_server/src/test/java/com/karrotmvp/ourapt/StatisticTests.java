package com.karrotmvp.ourapt;


import com.karrotmvp.ourapt.admin.StatisticRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatisticTests {

  @Autowired
  private StatisticRepository repo;

  @Test
  void statisticResultTest() {
    System.out.println(repo.countDailyActiveUser("2021-11-19"));
    System.out.println(repo.countFeedViewBetween("2021-11-19"));
    System.out.println(repo.countDailySigningUpUser("2021-11-18"));
  }

}
