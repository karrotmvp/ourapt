package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByUserId(String userId) {
    return this.userRepository.findById(userId)
      .orElseThrow(RegisteredUserNotFoundException::new);
  }

}