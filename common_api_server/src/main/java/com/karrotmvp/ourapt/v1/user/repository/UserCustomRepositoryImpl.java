package com.karrotmvp.ourapt.v1.user.repository;

import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
        User foundUser = em.find(User.class, userId);
        if (foundUser == null) {
            return Optional.empty();
        }
        KarrotProfile karrotOApiUserProfile = this.karrotOAPI.getKarrotUserProfileById(userId);
        foundUser.setProfile(karrotOApiUserProfile);
        return Optional.of(foundUser);
    }
}