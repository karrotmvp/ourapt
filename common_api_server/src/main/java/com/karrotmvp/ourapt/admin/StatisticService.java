package com.karrotmvp.ourapt.admin;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatisticService {
  private final StatisticRepository statisticRepository;
  private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  public long[] getLast7DaysDailyActiveUsers(Date pointOfView) {
    return Arrays.stream(getLast7DateFormats(pointOfView))
      .mapToLong((tString) -> this.statisticRepository.countDailyActiveUser(tString))
      .toArray();
  }

  public long[] getLast7DaysDailySigningUpUsers(Date pointOfView) {
    return Arrays.stream(getLast7DateFormats(pointOfView))
      .mapToLong((tString) -> this.statisticRepository.countDailySigningUpUser(tString))
      .toArray();
  }

  public long[] getLast7DaysSeeingFeedUsers(Date pointOfView) {
    return Arrays.stream(getLast7DateFormats(pointOfView))
      .mapToLong((tString) -> this.statisticRepository.countDailySeeingFeedUser(tString))
      .toArray();
  }

  public String[] getLast7DateFormats(Date pointOfView) {
    return IntStream.range(0, 8)
      .mapToObj(useCountedDateGetterFromAWeekAgo(pointOfView))
      .map((t) -> this.dateFormatter.format(t))
      .toArray(String[]::new);
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
