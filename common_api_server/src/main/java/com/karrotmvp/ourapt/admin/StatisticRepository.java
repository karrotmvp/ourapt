package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.log.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface StatisticRepository extends JpaRepository<RequestLog, Long> {

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE_FORMAT(created_at, '%Y-%m-%d') = ?1 " +
    "AND user_id IS NOT NULL) today_active_user;", nativeQuery = true)
  long countDailyActiveUser(
    String dateStringYYYYMMdd
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE_FORMAT(created_at, '%Y-%m-%d') = ?1 " +
    "AND path LIKE '/api/v1/apartment/%/question_' " +
    "AND method = 'GET') today_seeing_feed_user", nativeQuery = true)
  long countDailySeeingFeedUser(
    String dateStringYYYYMMdd
  );


  @Query(value = "SELECT COUNT(karrot_id) FROM " +
    "(SELECT karrot_id FROM user " +
    "WHERE DATE_FORMAT(created_at, '%Y-%m-%d') = ?1) today_signup"
    , nativeQuery = true)
  long countDailySigningUpUser(
    String dateStringYYYYMMdd
  );
}
