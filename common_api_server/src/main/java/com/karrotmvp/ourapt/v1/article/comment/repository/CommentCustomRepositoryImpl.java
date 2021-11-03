package com.karrotmvp.ourapt.v1.article.comment.repository;

import com.karrotmvp.ourapt.v1.article.comment.Comment;
import com.karrotmvp.ourapt.v1.common.BaseEntityCreatedDateComparator;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
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
  public List<Comment> findByParentIdOrderByCreatedAtAsc(String parentId) {
    TypedQuery<Comment> query = em.createQuery(
      "SELECT c FROM Comment c " +
        "LEFT JOIN FETCH c.writer " +
        "WHERE c.parent.id = ?1 ", Comment.class);
    query.setParameter(1, parentId);
    List<Comment> commentResults = query.getResultList();
    List<KarrotProfile> profiles = karrotOAPI.getKarrotUserProfilesByIds(
      commentResults.stream().map(cmt -> cmt.getWriter().getId()).collect(Collectors.toSet()));
    return Utils.leftOuterHashJoin(
        commentResults,
        profiles,
        (cmt) -> cmt.getWriter().getId(),
        KarrotProfile::getId,
        (cmt, kp) -> cmt.getWriter().setProfile(kp))
      .stream()
      .sorted(new BaseEntityCreatedDateComparator(BaseEntityCreatedDateComparator.Order.ASC))
      .collect(Collectors.toList());
  }
}
