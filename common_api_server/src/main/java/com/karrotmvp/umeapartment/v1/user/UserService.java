package com.karrotmvp.umeapartment.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public OAuthUserProfileDto getUserProfileFromDaangn(String accessToken) throws NotImplementedException {
    throw new NotImplementedException("getting me from daangn is not implemented"); 
  }

  public void addUser(OAuthUserProfileDto userProfile) {
    User newUser = userProfile.toEntity();
    userRepository.save(newUser);
  }
}