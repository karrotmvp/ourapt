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
    "AND user_id IS NOT NULL) daily_active_user;", nativeQuery = true)
  long countDailyActiveUser(
    String dateStringYYYYMMdd
  );


  @Query(value = "SELECT COUNT(karrot_id) FROM " +
    "(SELECT karrot_id FROM user " +
    "WHERE DATE_FORMAT(created_at, '%Y-%m-%d') = ?1 " +
    "AND created_at != '2021-11-26 15:46:38') daily_signup"
    , nativeQuery = true)
  long countDailySigningUpUser(
    String dateStringYYYYMMdd
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND path LIKE '/api/v1/apartment_' " +
    "AND method = 'GET') daily_apt_view", nativeQuery = true)
  long countApartmentViewBetween(
    String startDateString,
    String endDateString
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND path LIKE '/api/v1/no-apartmen_' " +
    "AND method = 'POST') daily_cancel_vote", nativeQuery = true)
  long countNoApartmentSubmitBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND path = '/api/v1/user/me/checkedIn' " +
    "AND method = 'PATCH') daily_apt_checkin", nativeQuery = true)
  long countCheckInBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND path LIKE '/api/v1/apartment/%/vote/pinned' " +
    "AND method = 'GET') daily_feed_view", nativeQuery = true)
  long countFeedViewBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND path LIKE '/api/v1/article/%/comments' " +
    "AND method = 'GET') daily_article_detail_view", nativeQuery = true)
  long countArticleDetailViewBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(writer_id) FROM " +
    "(SELECT DISTINCT writer_id FROM article " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND writer_id != 'ADMIN') daily_article_created", nativeQuery = true)
  long countArticleUserWriteBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(writer_id) FROM " +
    "(SELECT DISTINCT writer_id FROM comment " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND writer_id != 'ADMIN') daily_article_created", nativeQuery = true)
  long countCommentUserWriteBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND path LIKE '/api/v1/vote/item/%/voting' " +
    "AND method = 'POST') daily_do_vote", nativeQuery = true)
  long countDoVoteBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND path LIKE '/api/v1/vote/item/%/voting' " +
    "AND method = 'DELETE') daily_cancel_vote", nativeQuery = true)
  long countCancelVoteBetween(
    String startDateString,
    String startEndString
  );

}
