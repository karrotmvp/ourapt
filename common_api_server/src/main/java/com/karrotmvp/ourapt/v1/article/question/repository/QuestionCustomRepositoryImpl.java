package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.comment.repository.projection.CommentCount;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.*;
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
      KarrotProfile profileOfWriter =
        question.getWriter().isAdmin() ?
          makeAdminKarrotProfile(question.getWriter().getId()) :
          karrotOAPI.getKarrotUserProfileById(question.getWriter().getId());
      question.getWriter().setProfile(profileOfWriter);
      question.setCountOfComments(Math.toIntExact(countByParentId(question.getId())));
      return Optional.of(question);
    } catch (NoResultException ne) {
      return Optional.empty();
    }
  }

  @Override
  public List<Question> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "LEFT JOIN FETCH q.apartmentWhereCreated " +
        "WHERE q.apartmentWhereCreated.id = ?1 AND q.createdAt < ?2 " +
        "ORDER BY q.createdAt DESC", Question.class);

    query.setParameter(1, apartmentId);
    query.setParameter(2, dateCursor);
    query.setFirstResult(0);
    query.setMaxResults(pageable.getPageSize());

    return joinOnKarrotProfileAndCommentCount(query);
  }

  @Override
  public List<Question> findByApartmentIdAndPinned(String toWhereApartmentId) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q " +
        "FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "LEFT JOIN FETCH q.apartmentWhereCreated " +
        "WHERE q.apartmentWhereCreated.id = ?1 AND q.pinnedUntil >= ?2", Question.class);
    query.setParameter(1, toWhereApartmentId);
    query.setParameter(2, new Date());

    return joinOnKarrotProfileAndCommentCount(query);
  }

  @Override
  public List<Question> findByOffsetCursor(Pageable pageable) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "LEFT JOIN FETCH q.apartmentWhereCreated " +
        "ORDER BY q.createdAt DESC", Question.class);
    query.setFirstResult(Math.toIntExact(pageable.getOffset()));
    query.setMaxResults(pageable.getPageSize());
    return joinOnKarrotProfileAndCommentCount(query);
  }

  private List<Question> joinOnKarrotProfileAndCommentCount(TypedQuery<Question> query) {
    Set<String> writerIds = new HashSet<>();
    Set<String> questionIds = new HashSet<>();
    List<Question> incompleteQuestions = query.getResultList()
      .stream()
      .peek(q -> writerIds.add(q.getWriter().getId()))
      .peek(q -> questionIds.add(q.getId()))
      .collect(Collectors.toList());

    incompleteQuestions = Utils.leftOuterHashJoin(
      incompleteQuestions,
      karrotOAPI.getKarrotUserProfilesByIds(writerIds),
      (q) -> q.getWriter().getId(),
      KarrotProfile::getId,
      (q, profile) -> q.getWriter().setProfile(profile));

    // TODO:: writer가 관리자일때 체크해서 관리자 프로필 넣어주기

    return Utils.leftOuterHashJoin(
      incompleteQuestions,
      this.findCountPerParentIdIn(questionIds),
      Question::getId,
      CommentCount::getParentId,
      (q, cc) -> q.setCountOfComments(Math.toIntExact(cc.getCommentCount())));
  }

  private List<CommentCount> findCountPerParentIdIn(Set<String> parentIds) {
    TypedQuery<CommentCount> query = em.createQuery(
      "SELECT new com.karrotmvp.ourapt.v1.article.comment.repository.projection.CommentCount(c.parent.id, COUNT(c)) " +
        "FROM Comment c WHERE c.parent.id IN ?1 " +
        "GROUP BY c.parent.id", CommentCount.class);
    query.setParameter(1, parentIds);
    return query.getResultList();
  }

  private long countByParentId(String parentId) {
    TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Comment c WHERE c.parent.id = ?1", Long.class);
    query.setParameter(1, parentId);
    return query.getSingleResult();
  }


  private KarrotProfile makeAdminKarrotProfile(String userId) {
    return new KarrotProfile(userId, "우리아파트", "");
  }

}
