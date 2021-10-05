package com.karrotmvp.umeapartment.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void addUser(UserProfileDto userProfile) {
    User newUser = userProfile.toEntity();
    userRepository.save(newUser);
  }
}