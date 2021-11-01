package com.karrotmvp.ourapt.v1.article.question.repository;

import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class QuestionCustomRepositoryImpl implements QuestionCustomRepository<Question, String>{

  private final EntityManager em;

  private final KarrotOAPI karrotOAPI;

  public QuestionCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    this.em = em;
    this.karrotOAPI = karrotOAPI;
  }


  @Override
  public List<Question> findFirstByDateCursorByOrderByDesc(Date dateCursor, Pageable pageable) {
    TypedQuery<Question> query = em.createQuery("SELECT q " +
      "FROM Question q " +
      "WHERE q.createdAt < ?1 " +
      "ORDER BY q.createdAt DESC", Question.class);
    query.setParameter(1, dateCursor);
    query.setFirstResult(0);
    query.setMaxResults(pageable.getPageSize());
    List<Question> resultQuestions = query.getResultList();
    // join in application level
    return query.getResultList();
  }
}
