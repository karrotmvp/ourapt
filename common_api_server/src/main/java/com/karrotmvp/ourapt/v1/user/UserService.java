package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.dto.UserDto;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ModelMapper mapper;
  public UserService(UserRepository userRepository, ModelMapper mapper) {
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  public UserDto getUserByUserId(String userId) {
    User found = this.userRepository.findById(userId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    return mapper.map(found, UserDto.class);
  }

}