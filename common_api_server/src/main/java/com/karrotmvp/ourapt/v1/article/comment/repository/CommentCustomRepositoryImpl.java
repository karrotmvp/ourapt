package com.karrotmvp.ourapt.v1.article.comment.repository;

import com.karrotmvp.ourapt.v1.article.ArticleBaseCustomRepository;
import com.karrotmvp.ourapt.v1.article.comment.Comment;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public class CommentCustomRepositoryImpl extends ArticleBaseCustomRepository<Comment> implements CommentCustomRepository {

  public CommentCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    super(em, karrotOAPI);
  }

  @Override
  public Optional<Comment> findById(String commentId) {
    TypedQuery<Comment> query = em.createQuery(
      "SELECT c FROM Comment c " +
        "LEFT JOIN FETCH c.writer " +
        "LEFT JOIN FETCH c.apartmentWhereCreated " +
        "WHERE c.id = ?1", Comment.class);
    query.setParameter(1, commentId);
    try {
      Comment comment = query.getSingleResult();
      comment.getWriter().setProfile(
        comment.getWriter().isAdmin() ?
          Static.makeAdminKarrotProfile(comment.getWriter().getId()) :
          karrotOAPI.getKarrotUserProfileById(comment.getWriter().getId())
      );
      return Optional.of(comment);
    } catch (NoResultException ne) {
      return Optional.empty();
    }
  }

  @Override
  public List<Comment> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented Yet");
  }

  @Override
  public List<Comment> findAllByApartmentIdOrderByCreatedAtDesc(String apartmentId) {
    throw new UnsupportedOperationException("Not implemented Yet");
  }

  @Override
  public List<Comment> findByOffsetCursor(Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented Yet");
  }

  @Override
  public List<Comment> findByParentId(String parentId) {
    TypedQuery<Comment> query = em.createQuery(
      "SELECT c FROM Comment c " +
        "LEFT JOIN FETCH c.writer " +
        "WHERE c.parent.id = ?1 AND c.deletedAt IS NULL", Comment.class);
    query.setParameter(1, parentId);
    return joinOnKarrotProfile(query);
  }

  @Override
  public List<Comment> findByParentIdIn(Set<String> parentIds) {
    TypedQuery<Comment> query = em.createQuery(
      "SELECT c FROM Comment c " +
        "LEFT JOIN FETCH c.writer " +
        "WHERE c.parent.id IN ?1 AND c.deletedAt IS NULL", Comment.class);
    query.setParameter(1, parentIds);
    return joinOnKarrotProfile(query);
  }

}
