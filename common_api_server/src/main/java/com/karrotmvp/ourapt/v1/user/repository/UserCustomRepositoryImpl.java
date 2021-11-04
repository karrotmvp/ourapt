package com.karrotmvp.ourapt.v1.user.repository;

import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;


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
            User foundUser =  query.getSingleResult();
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

    private KarrotProfile makeAdminKarrotProfile(String userId) {
        return new KarrotProfile(userId, "우리아파트", "");
    }
}
