package com.karrotmvp.ourapt.v1.comment.repository;

import com.karrotmvp.ourapt.v1.comment.Comment;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CommentCustomRepositoryImpl implements CommentCustomRepository<Comment, String> {

  private final EntityManager em;
  private final KarrotOAPI karrotOAPI;

  public CommentCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    this.em = em;
    this.karrotOAPI = karrotOAPI;
  }

  @Override
  public Optional<Comment> findById(String commentId) {
    TypedQuery<Comment> query = em.createQuery(
      "SELECT c FROM Comment c " +
        "LEFT JOIN FETCH c.writer " +
        "WHERE c.id = ?1", Comment.class);
    query.setParameter(1, commentId);
    try {
      Comment comment = query.getSingleResult();
      return Optional.of(comment);
      // TODO: Fill writer profile via karrotOAPI
    } catch (NoResultException ne) {
      return Optional.empty();
    }
  }

  @Override
  public List<Comment> findByParentIdOrderByCreatedAtAsc(String parentId) {
    TypedQuery<Comment> query = em.createQuery(
      "SELECT c FROM Comment c " +
        "LEFT JOIN FETCH c.writer " +
        "WHERE c.parent.id = ?1 AND c.deletedAt IS NULL " +
        "ORDER BY c.createdAt ASC", Comment.class);
    query.setParameter(1, parentId);
    List<Comment> commentResults = query.getResultList()
      .stream()
      .peek(c -> c.getWriter().setProfile(c.getWriter().isAdmin() ? Static.makeAdminKarrotProfile(c.getId()) : null))
      .collect(Collectors.toList());

    List<KarrotProfile> profiles = karrotOAPI.getKarrotUserProfilesByIds(
      commentResults.stream().map(cmt -> cmt.getWriter().getId()).collect(Collectors.toSet()));
    return Utils.leftOuterHashJoin(
      commentResults,
      profiles,
      (cmt) -> cmt.getWriter().getId(),
      KarrotProfile::getId,
      (cmt, kp) -> cmt.getWriter().setProfile(kp));
  }
}
