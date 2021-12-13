package com.karrotmvp.ourapt.v1.article.vote.repository;

import com.karrotmvp.ourapt.v1.article.ArticleBaseCustomRepository;
import com.karrotmvp.ourapt.v1.article.vote.entity.Vote;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VoteCustomRepositoryImpl extends ArticleBaseCustomRepository<Vote> implements VoteCustomRepository {

  public VoteCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    super(em, karrotOAPI);
  }

  // SELECT시 N+1 중복 때문에 OneToMany 관계는 eagar loading batchsize
  @Override
  public Optional<Vote> findById(String voteId) {
    TypedQuery<Vote> query = em.createQuery(
      "SELECT v FROM Vote v " +
        "LEFT JOIN FETCH v.writer " +
        "LEFT JOIN FETCH v.apartmentWhereCreated " +
        "WHERE v.id = ?1", Vote.class);
    query.setParameter(1, voteId);
    try {
      Vote vote = query.getSingleResult();
      vote.getWriter().setProfile(
        vote.getWriter().isAdmin() ?
          Static.makeAdminKarrotProfile(vote.getWriter().getId()) :
          karrotOAPI.getKarrotUserProfileById(vote.getWriter().getId())
      );
      vote.setCountOfComments(Math.toIntExact(this.commentCountForParentId(vote.getId())));
      vote.sortItems();
      return Optional.of(vote);
    } catch (NoResultException ne) {
      return Optional.empty();
    }
  }

  private long commentCountForParentId(String parentId) {
    TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Comment c " +
      "WHERE " +
        "(c.parent.id = ?1 " +
            "OR c.parent.id IN " +
              "(SELECT subc.id FROM Comment subc WHERE subc.parent.id = ?1)" +
        ") " +
        "AND c.deletedAt IS NULL", Long.class);
    query.setParameter(1, parentId);
    return query.getSingleResult();
  }


  @Override
  public List<Vote> findFirstByApartmentIdToAndDateCursorByOrderByDesc(String apartmentId, Date dateCursor, Pageable pageable) {
    TypedQuery<Vote> query = em.createQuery(
      "SELECT v FROM Vote v " + 
        "LEFT JOIN FETCH v.writer " +
        "LEFT JOIN FETCH v.apartmentWhereCreated " +
        "WHERE v.apartmentWhereCreated.id = ?1 " +
        "AND v.createdAt < ?2 " +
        "AND v.deletedAt IS NULL " +
        "ORDER BY v.createdAt DESC", Vote.class);

    query.setParameter(1, apartmentId);
    query.setParameter(2, dateCursor);
    query.setFirstResult(0);
    query.setMaxResults(pageable.getPageSize());

    return completeTransientAttribute(query);
  }

  @Override
  public List<Vote> findAllByApartmentIdOrderByCreatedAtDesc(String apartmentId) {
    TypedQuery<Vote> query = em.createQuery(
      "SELECT v FROM Vote v " +
        "LEFT JOIN FETCH v.writer " +
        "LEFT JOIN FETCH v.apartmentWhereCreated " +
        "WHERE v.apartmentWhereCreated.id = ?1 " +
        "AND v.deletedAt IS NULL " +
        "ORDER BY v.createdAt DESC", Vote.class);
    query.setParameter(1, apartmentId);
    return completeTransientAttribute(query);
  }

  @Override
  public List<Vote> findByOffsetCursor(Pageable pageable) {
    TypedQuery<Vote> query = em.createQuery(
      "SELECT v FROM Vote v " +
        "LEFT JOIN FETCH v.items " +
        "LEFT JOIN FETCH v.writer " +
        "LEFT JOIN FETCH v.apartmentWhereCreated " +
        "WHERE v.deletedAt IS NULL " +
        "ORDER BY v.createdAt DESC", Vote.class);
    query.setFirstResult(Math.toIntExact(pageable.getOffset()));
    query.setMaxResults(pageable.getPageSize());
    return completeTransientAttribute(query);
  }

//  private long commentCountForParentIds(Set<String> parentIds) {
//    TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Comment c " +
//      "WHERE (c.parent.id IN ?1 OR c.parent.id IN (SELECT subc.id FROM Comment subc)) " +
//      "AND c.deletedAt IS NULL", Long.class);
//    query.setParameter(1, parentId);
//    return query.getSingleResult();
//  }

  private List<Vote> completeTransientAttribute(TypedQuery<Vote> query) {
    return joinOnKarrotProfile(query);
  }


  @Override
  protected List<Vote> joinOnKarrotProfile(TypedQuery<Vote> query) {
    return super.joinOnKarrotProfile(query).stream()
      .peek(Vote::sortItems)
      .collect(Collectors.toList());
  }

}
