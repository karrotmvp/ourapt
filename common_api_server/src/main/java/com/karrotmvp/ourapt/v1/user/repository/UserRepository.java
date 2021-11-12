package com.karrotmvp.ourapt.v1.user.repository;

import com.karrotmvp.ourapt.v1.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository<User, String> {
  Page<User> findAll(Pageable pageable);
  long countByDeletedAtIsNull();
}
