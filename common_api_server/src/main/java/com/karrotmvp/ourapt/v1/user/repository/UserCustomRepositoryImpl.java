package com.karrotmvp.ourapt.v1.user.repository;

import com.karrotmvp.ourapt.v1.common.BaseEntityCreatedDateComparator;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository
@Transactional
public class UserCustomRepositoryImpl implements UserCustomRepository<User, String> {

  private final EntityManager em;

  private final KarrotOAPI karrotOAPI;

  public UserCustomRepositoryImpl(EntityManager em, KarrotOAPI karrotOAPI) {
    this.em = em;
    this.karrotOAPI = karrotOAPI;
  }

  @Override
  public Optional<User> findById(String userId) {
    TypedQuery<User> query = em.createQuery(
      "SELECT u " +
        "FROM User u LEFT JOIN FETCH u.checkedIn " +
        "WHERE u.id = ?1", User.class);
    query.setParameter(1, userId);
    try {
      User foundUser = query.getSingleResult();
      if (!foundUser.isAdmin()) {
        KarrotProfile karrotOApiUserProfile = this.karrotOAPI.getKarrotUserProfileById(userId);
        foundUser.setProfile(karrotOApiUserProfile);
      } else {
        foundUser.setProfile(makeAdminKarrotProfile(foundUser.getId()));
      }
      return Optional.of(foundUser);
    } catch (NoResultException ne) {
      return Optional.empty();
    }
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    TypedQuery<User> query = em.createQuery(
      "SELECT u " +
        "FROM User u LEFT JOIN FETCH u.checkedIn " +
        "ORDER BY u.createdAt DESC", User.class);
    query.setFirstResult(Math.toIntExact(pageable.getOffset()));
    query.setMaxResults(pageable.getPageSize());
    List<User> adminUsers = new ArrayList<>();
    Set<String> normalUserIds = new HashSet<>();
    List<User> normalUsers = query.getResultList().stream()
      .peek(u -> {
        if (u.isAdmin()) {
          u.setProfile(makeAdminKarrotProfile(u.getId()));
          adminUsers.add(u);
        }
      })
      .filter((u) -> !u.isAdmin())
      .peek(nu -> normalUserIds.add(nu.getId()))
      .collect(Collectors.toList());
    List<KarrotProfile> normalUserProfiles = karrotOAPI.getKarrotUserProfilesByIds(normalUserIds);

    return new PageImpl<>(
      Utils.leftOuterHashJoin(
          Stream.concat(adminUsers.stream(), normalUsers.stream()).collect(Collectors.toList()),
          normalUserProfiles,
          User::getId,
          KarrotProfile::getId,
          User::setProfile
        ).stream()
        .sorted(new BaseEntityCreatedDateComparator(BaseEntityCreatedDateComparator.Order.DESC))
        .collect(Collectors.toList()));
  }

  private KarrotProfile makeAdminKarrotProfile(String userId) {
    return new KarrotProfile(userId, "우리아파트", "");
  }
}
