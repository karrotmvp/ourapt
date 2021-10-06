package com.karrotmvp.ourapt.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void addUser(User newUser) {
    userRepository.save(newUser);
  }
}