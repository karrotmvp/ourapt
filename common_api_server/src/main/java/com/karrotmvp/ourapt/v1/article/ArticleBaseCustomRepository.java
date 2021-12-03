package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.comment.repository.projection.CommentCount;
import com.karrotmvp.ourapt.v1.common.BaseEntityCreatedDateComparator;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ArticleBaseCustomRepository<T extends Article> {

  protected final EntityManager em;
  protected final KarrotOAPI karrotOAPI;
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  protected ArticleBaseCustomRepository(EntityManager em, KarrotOAPI karrotOAPI) {
    this.em = em;
    this.karrotOAPI = karrotOAPI;
  }


  protected List<CommentCount> findCountPerParentIdIn(Set<String> parentIds) {
    TypedQuery<CommentCount> query = em.createQuery(
      "SELECT new " + CommentCount.class.getName() + "(c.parent.id, COUNT(c)) " +
        "FROM Comment c WHERE c.parent.id IN ?1 AND c.deletedAt IS NULL " +
        "GROUP BY c.parent.id", CommentCount.class);
    query.setParameter(1, parentIds);
    return query.getResultList();
  }

  protected long countByParentId(String parentId) {
    TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Comment c WHERE c.parent.id = ?1 AND c.deletedAt IS NULL", Long.class);
    query.setParameter(1, parentId);
    return query.getSingleResult();
  }

  protected List<T> joinOnKarrotProfileAndCommentCount(TypedQuery<T> query) {
    Set<String> writerIds = new HashSet<>();
    Set<String> questionIds = new HashSet<>();
    List<T> incompleteQuestions = query.getResultList()
      .stream()
      .peek(article -> writerIds.add(article.getWriter().getId()))
      .peek(article -> questionIds.add(article.getId()))
      .peek(article -> article.getWriter().setProfile(article.isByAdmin() ? Static.makeAdminKarrotProfile(article.getId()) : null))
      .collect(Collectors.toList());
    List<KarrotProfile> profiles = this.karrotOAPI.getKarrotUserProfilesByIds(writerIds);

    incompleteQuestions = Utils.leftOuterHashJoin(
      incompleteQuestions,
      profiles,
      (article) -> article.getWriter().getId(),
      KarrotProfile::getId,
      (article, profile) -> article.getWriter().setProfile(profile));

    return Utils.leftOuterHashJoin(
        incompleteQuestions,
        this.findCountPerParentIdIn(questionIds),
        Article::getId,
        CommentCount::getParentId,
        (q, cc) -> q.setCountOfComments(Math.toIntExact(cc.getCommentCount())))
      .stream()
      .sorted(new BaseEntityCreatedDateComparator(BaseEntityCreatedDateComparator.Order.DESC))
      .collect(Collectors.toList());
  }
}