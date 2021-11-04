package com.karrotmvp.ourapt.v1.user.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserCustomRepository<T, ID> {
    Optional<T> findById(ID userId);
    Page<T> findAll(Pageable pageable);
}
