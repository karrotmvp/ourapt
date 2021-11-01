package com.karrotmvp.ourapt.v1.user.repository;

import com.karrotmvp.ourapt.v1.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository<User, String> {
}
