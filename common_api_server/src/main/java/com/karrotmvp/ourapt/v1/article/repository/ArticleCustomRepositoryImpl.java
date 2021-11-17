package com.karrotmvp.ourapt.v1.article.repository;

import com.karrotmvp.ourapt.v1.article.Article;
import com.karrotmvp.ourapt.v1.comment.repository.projection.CommentCount;
import com.karrotmvp.ourapt.v1.common.BaseEntityCreatedDateComparator;
import com.karrotmvp.ourapt.v1.common.Static;
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
public class ArticleCustomRepositoryImpl<T extends Article> implements ArticleCustomRepository<T, String> {

  private final EntityManager em;

  private final KarrotOAPI karrotOAPI;

  public ArticleCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    this.em = em;
    this.karrotOAPI = karrotOAPI;
  }

  @Override
  public Optional<T> findById(String articleId, Class<T> resultClass) {
    TypedQuery<T> query = em.createQuery(
      "SELECT a FROM " + resultClass.getSimpleName() + " a " +
        "LEFT JOIN FETCH a.writer " +
        "LEFT JOIN FETCH a.apartmentWhereCreated " +
        "WHERE a.id = ?1", T.class);
    query.setParameter(1, articleId);
    try {
      T article = query.getSingleResult();
      KarrotProfile profileOfWriter =
        article.getWriter().isAdmin() ?
          Static.makeAdminKarrotProfile(article.getWriter().getId()) :
          karrotOAPI.getKarrotUserProfileById(article.getWriter().getId());
      article.getWriter().setProfile(profileOfWriter);
      article.setCountOfComments(Math.toIntExact(countByParentId(article.getId())));
      return Optional.of(article);
    } catch (NoResultException ne) {
      return Optional.empty();
    }
  }

  @Override
  public List<T> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable, Class<T> resultClass) {
    TypedQuery<T> query = em.createQuery(
      "SELECT a FROM " + resultClass.getSimpleName() + " a " +
        "LEFT JOIN FETCH a.writer " +
        "LEFT JOIN FETCH a.apartmentWhereCreated " +
        "WHERE a.deletedAt IS NULL " +
        "ORDER BY a.createdAt DESC", resultClass);
    query.setFirstResult(Math.toIntExact(pageable.getOffset()));
    query.setMaxResults(pageable.getPageSize());
    return joinOnKarrotProfileAndCommentCount(query);
  }

  @Override
  public List<T> findByApartmentIdAndPinned(String apartmentId, Class<T> resultClass) {
    TypedQuery<T> query = em.createQuery(
      "SELECT a " +
        "FROM " + resultClass.getSimpleName() + " a " +
        "LEFT JOIN FETCH a.writer " +
        "LEFT JOIN FETCH a.apartmentWhereCreated " +
        "WHERE a.apartmentWhereCreated.id = ?1 AND a.pinnedUntil >= ?2 AND a.deletedAt IS NULL", resultClass);
    query.setParameter(1, apartmentId);
    query.setParameter(2, new Date());
    return joinOnKarrotProfileAndCommentCount(query);
  }

  @Override
  public List<T> findByOffsetCursor(Pageable pageable, Class<T> resultClass) {
    TypedQuery<T> query = em.createQuery(
      "SELECT a FROM " + resultClass.getSimpleName() + " a " +
        "LEFT JOIN FETCH a.writer " +
        "LEFT JOIN FETCH a.apartmentWhereCreated " +
        "WHERE a.deletedAt IS NULL " +
        "ORDER BY a.createdAt DESC", resultClass);
    query.setFirstResult(Math.toIntExact(pageable.getOffset()));
    query.setMaxResults(pageable.getPageSize());
    return joinOnKarrotProfileAndCommentCount(query);
  }

  private List<T> joinOnKarrotProfileAndCommentCount(TypedQuery<T> query) {
    Set<String> writerIds = new HashSet<>();
    Set<String> articleIds = new HashSet<>();

    List<T> incompleteArticles = query.getResultStream()
      .peek(a -> writerIds.add(a.getWriter().getId()))
      .peek(a -> articleIds.add(a.getId()))
      .peek(a -> a.getWriter().setProfile(a.isByAdmin() ? Static.makeAdminKarrotProfile(a.getId()) : null))
      .collect(Collectors.toList());

    incompleteArticles = Utils.leftOuterHashJoin(
      incompleteArticles,
      karrotOAPI.getKarrotUserProfilesByIds(writerIds),
      (a) -> a.getWriter().getId(),
      KarrotProfile::getId,
      (a, profile) -> a.getWriter().setProfile(profile));

    return Utils.leftOuterHashJoin(
        incompleteArticles,
        this.findCountPerParentIdIn(articleIds),
        Article::getId,
        CommentCount::getParentId,
        (a, cc) ->
          a.setCountOfComments(Math.toIntExact(cc.getCommentCount())))
      .stream()
      .sorted(new BaseEntityCreatedDateComparator(BaseEntityCreatedDateComparator.Order.DESC))
      .collect(Collectors.toList());
  }

  private List<CommentCount> findCountPerParentIdIn(Set<String> resultClass) {
    TypedQuery<CommentCount> query = em.createQuery(
      "SELECT new " + CommentCount.class.getName() + "(c.parent.id, COUNT(c)) " +
        "FROM Comment c WHERE c.parent.id IN ?1 AND c.deletedAt IS NULL " +
        "GROUP BY c.parent.id", CommentCount.class);
    query.setParameter(1, resultClass);
    return query.getResultList();
  }

  private long countByParentId(String parentId) {
    TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Comment c WHERE c.parent.id = ?1 AND c.deletedAt IS NULL", Long.class);
    query.setParameter(1, parentId);
    return query.getSingleResult();
  }
}
