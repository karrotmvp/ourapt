package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.common.BaseEntityCreatedDateComparator;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class QuestionCustomRepositoryImpl implements QuestionCustomRepository<Question, String> {

  private final EntityManager em;

  private final KarrotOAPI karrotOAPI;

  public QuestionCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    this.em = em;
    this.karrotOAPI = karrotOAPI;
  }

  @Override
  public Optional<Question> findById(String questionId) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "WHERE q.id = ?1", Question.class);
    query.setParameter(1, questionId);
    try {
      Question question = query.getSingleResult();
      KarrotProfile profileOfWriter = karrotOAPI.getKarrotUserProfileById(question.getWriter().getId());
      question.getWriter().setProfile(profileOfWriter);
      return Optional.of(question);
    } catch (NoResultException ne) {
      return Optional.empty();
    }
  }

  @Override
  public List<Question> findFirstByExposedToAndDateCursorByOrderByDesc(String apartmentIdToBeExposed, Date dateCursor, Pageable pageable) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT e.question FROM Exposure e " +
        "LEFT JOIN FETCH e.question.writer " +
        "WHERE e.toWhere.id = ?1 AND e.question.createdAt < ?2 " +
        "ORDER BY e.question.createdAt DESC", Question.class);

    query.setParameter(1, apartmentIdToBeExposed);
    query.setParameter(2, dateCursor);
    query.setFirstResult(0);
    query.setMaxResults(pageable.getPageSize());
    List<Question> questions = query.getResultList();
    List<KarrotProfile> profiles = karrotOAPI.getKarrotUserProfilesByIds(
      questions.stream().map(q -> q.getWriter().getId()).collect(Collectors.toSet())
    );

    return Utils.leftOuterHashJoin(
        questions,
        profiles,
        (q) -> q.getWriter().getId(),
        KarrotProfile::getId,
        (a, kp) -> a.getWriter().setProfile(kp))
      .stream()
      .sorted(new BaseEntityCreatedDateComparator(BaseEntityCreatedDateComparator.Order.DESC))
      .collect(Collectors.toList());
  }

  @Override
  public List<Question> findByExposurePinnedAndToWhere(String toWhereApartmentId) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT e.question " +
        "FROM Exposure e " +
        "LEFT JOIN FETCH e.question.writer " +
        "WHERE e.toWhere.id = ?1 AND e.pinnedUntil >= ?2", Question.class);
    query.setParameter(1, toWhereApartmentId);
    query.setParameter(2, new Date());
    List<Question> questions = query.getResultList();
    List<KarrotProfile> profiles = karrotOAPI.getKarrotUserProfilesByIds(
      questions.stream().map(q -> q.getWriter().getId()).collect(Collectors.toSet()));
    return Utils.leftOuterHashJoin(
      questions,
      profiles,
      (q) -> q.getWriter().getId(),
      KarrotProfile::getId,
      (p, profile) -> p.getWriter().setProfile(profile));
  }

  private KarrotProfile makeAdminKarrotProfile(String userId) {
    return new KarrotProfile(userId, "우리아파트", "");
  }

}
