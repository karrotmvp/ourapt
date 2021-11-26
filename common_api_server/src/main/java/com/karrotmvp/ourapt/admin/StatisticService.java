package com.karrotmvp.ourapt.admin;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class StatisticService {
  private final StatisticRepository statisticRepository;
  private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  public long[] getLast7DaysDailyActiveUsers(Date pointOfView) {
    return Arrays.stream(getLast7DateFormats(pointOfView))
      .mapToLong(this.statisticRepository::countDailyActiveUser)
      .toArray();
  }

  public long[] getLast7DaysDailySigningUpUsers(Date pointOfView) {
    return Arrays.stream(getLast7DateFormats(pointOfView))
      .mapToLong(this.statisticRepository::countDailySigningUpUser)
      .toArray();
  }

  public long[] getLast7DaysSeeingFeedUsers(Date pointOfView) {
    return Arrays.stream(getLast7DateFormats(pointOfView))
      .mapToLong(this.statisticRepository::countDailyFeedView)
      .toArray();
  }

  public String[] getLast7DateFormats(Date pointOfView) {
    return IntStream.range(0, 8)
      .mapToObj(useCountedDateGetterFromAWeekAgo(pointOfView))
      .map(this.dateFormatter::format)
      .toArray(String[]::new);
  }

  public long[] getFunnelOfDaily(Date pointOfView) {
    String formattedDate = this.dateFormatter.format(pointOfView);
    return new long[] {
      this.statisticRepository.countDailyApartmentView(formattedDate),
      this.statisticRepository.countDailyNoApartmentSubmit(formattedDate),
      this.statisticRepository.countDailyCheckInView(formattedDate),
      this.statisticRepository.countDailyFeedView(formattedDate),
      this.statisticRepository.countDailyArticleDetailView(formattedDate),
      this.statisticRepository.countDailyArticleUserWrite(formattedDate),
      this.statisticRepository.countDailyCommentUserWrite(formattedDate),
      this.statisticRepository.countDailyDoVote(formattedDate),
      this.statisticRepository.countDailyCancelVote(formattedDate)
    };
  }


  private IntFunction<Date> useCountedDateGetterFromAWeekAgo(Date pointOfView) {
    return (i) -> {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(pointOfView);
      calendar.add(Calendar.DATE, (-7 + i));
      return calendar.getTime();
    };
  }
}
