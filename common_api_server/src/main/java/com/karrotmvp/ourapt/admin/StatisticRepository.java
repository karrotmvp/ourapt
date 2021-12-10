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
    "AND (path LIKE '/api/v1/apartment/%/vote/pinned' OR path LIKE '/api/v1/apartment/%/votes') " +
    "AND method = 'GET') daily_feed_view", nativeQuery = true)
  long countFeedViewBetween(
    String startDateString,
    String startEndString
  );

  @Query(value = "SELECT COUNT(user_id) FROM " +
    "(SELECT DISTINCT user_id FROM request_log " +
    "WHERE DATE(created_at) >= ?1 and DATE(created_at) <= ?2 " +
    "AND (path LIKE '/api/v1/article/%/comments' OR path LIKE '/api/v1/vote/________-____-____-____-____________') " +
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

  ;

  @Query(value =
    "SELECT COUNT(DISTINCT(frl.user_id)) as first_user_id " +
      "FROM first_request_log frl " +
      "INNER JOIN request_log rl ON frl.user_id = rl.user_id " +
      "AND DATE_FORMAT(frl.created_at, '%Y-%m-%d') = DATE_FORMAT(rl.created_at, '%Y-%m-%d') " +
      "AND DATE_FORMAT(frl.created_at, '%Y-%m-%d') = ?1 " +
      "AND (path LIKE '/api/v1/apartment/%/vote/pinned' OR path LIKE '/api/v1/apartment/%/votes') " +
      "AND method = 'GET'", nativeQuery = true)
  Long countDailyFirstFeedView(String dateString);

  @Query(value =
    "SELECT COUNT(DISTINCT(rl2.user_id)) as retention_user_id " +
      "FROM request_log rl2 " +
      "INNER JOIN " +
      "( " +
      "SELECT frl.user_id, rl1.path, rl1.method, frl.created_at " +
      "FROM first_request_log frl " +
      "INNER JOIN request_log rl1 ON frl.user_id = rl1.user_id " +
      "AND DATE_FORMAT(frl.created_at, '%Y-%m-%d') = DATE_FORMAT(rl1.created_at, '%Y-%m-%d') " +
      ") ffv " +
      "ON ffv.user_id = rl2.user_id " +
      "AND DATE_FORMAT(ffv.created_at, '%Y-%m-%d') = ?1 " +
      "AND DATE_FORMAT(rl2.created_at, '%Y-%m-%d') = ?2 " +
      "AND (ffv.path LIKE '/api/v1/apartment/%/vote/pinned' OR ffv.path LIKE '/api/v1/apartment/%/votes') " +
      "AND ffv.method = 'GET' " +
      "AND (rl2.path LIKE '/api/v1/apartment/%/vote/pinned' OR rl2.path LIKE '/api/v1/apartment/%/votes') " +
      "AND rl2.method = 'GET';", nativeQuery = true)
  Long countRetentionUsers(String firstVisitDateString, String comparingDateString);
}
