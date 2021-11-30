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

  public String[] getLast7DateFormats(Date pointOfView) {
    return IntStream.range(0, 8)
      .mapToObj(useCountedDateGetterFromAWeekAgo(pointOfView))
      .map(this.dateFormatter::format)
      .toArray(String[]::new);
  }

  public long[] getFunnelBetween(Date startDate, Date endDate) {
    String formattedStartDate = this.dateFormatter.format(startDate);
    String formattedEndDate = this.dateFormatter.format(endDate);
    return new long[] {
      this.statisticRepository.countApartmentViewBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countNoApartmentSubmitBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countCheckInBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countFeedViewBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countArticleDetailViewBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countArticleUserWriteBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countCommentUserWriteBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countDoVoteBetween(formattedStartDate, formattedEndDate),
      this.statisticRepository.countCancelVoteBetween(formattedStartDate, formattedEndDate)
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
