package com.karrotmvp.ourapt.v1.user.repository;


import java.util.Optional;

public interface UserCustomRepository<T, ID> {
    Optional<T> findById(ID userId);
}
