package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.ArticleBaseCustomRepository;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.common.Static;
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

@Repository
@Transactional
public class QuestionCustomRepositoryImpl extends ArticleBaseCustomRepository<Question> implements QuestionCustomRepository {

  public QuestionCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    super(em, karrotOAPI);
  }

  @Override
  public Optional<Question> findById(String questionId) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "LEFT JOIN FETCH q.apartmentWhereCreated " +
        "WHERE q.id = ?1", Question.class);
    query.setParameter(1, questionId);
    try {
      Question question = query.getSingleResult();
      KarrotProfile profileOfWriter =
        question.getWriter().isAdmin() ?
          Static.makeAdminKarrotProfile(question.getWriter().getId()) :
          karrotOAPI.getKarrotUserProfileById(question.getWriter().getId());
      question.getWriter().setProfile(profileOfWriter);
      question.setCountOfComments(Math.toIntExact(this.countByParentId(question.getId())));
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
        "WHERE q.apartmentWhereCreated.id = ?1 AND q.createdAt < ?2 AND q.deletedAt IS NULL " +
        "ORDER BY q.createdAt DESC", Question.class);

    query.setParameter(1, apartmentId);
    query.setParameter(2, dateCursor);
    query.setFirstResult(0);
    query.setMaxResults(pageable.getPageSize());
    return joinOnKarrotProfileAndCommentCount(query);
  }

  @Override
  public List<Question> findByApartmentIdAndPinned(String apartmentId) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "LEFT JOIN FETCH q.apartmentWhereCreated " +
        "WHERE q.apartmentWhereCreated.id = ?1 AND q.pinnedUntil >= ?2 AND q.deletedAt IS NULL", Question.class);
    query.setParameter(1, apartmentId);
    query.setParameter(2, new Date());
    return joinOnKarrotProfileAndCommentCount(query);
  }

  @Override
  public List<Question> findByOffsetCursor(Pageable pageable) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "LEFT JOIN FETCH q.apartmentWhereCreated " +
        "WHERE q.deletedAt IS NULL " +
        "ORDER BY q.createdAt DESC", Question.class);
    query.setFirstResult(Math.toIntExact(pageable.getOffset()));
    query.setMaxResults(pageable.getPageSize());
    return joinOnKarrotProfileAndCommentCount(query);
  }

  @Override
  public List<Question> findByAboutId(String voteId) {
    TypedQuery<Question> query = em.createQuery(
      "SELECT q FROM Question q " +
        "LEFT JOIN FETCH q.writer " +
        "LEFT JOIN FETCH q.apartmentWhereCreated " +
        "WHERE q.deletedAt IS NULL " +
        "AND q.about.id = ?1", Question.class
    );
    query.setParameter(1, voteId);
    return joinOnKarrotProfileAndCommentCount(query);
  }
}