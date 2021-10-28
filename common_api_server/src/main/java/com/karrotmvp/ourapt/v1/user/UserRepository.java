package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
