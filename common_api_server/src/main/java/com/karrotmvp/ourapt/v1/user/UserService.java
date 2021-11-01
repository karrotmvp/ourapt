package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.apartment.ApartmentRepository;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ApartmentRepository apartmentRepository;
  private final ModelMapper mapper;
  public UserService(UserRepository userRepository, ApartmentRepository apartmentRepository, ModelMapper mapper) {
    this.userRepository = userRepository;
    this.apartmentRepository = apartmentRepository;
    this.mapper = mapper;
  }

  public UserDto getUserById(String userId) {
    User found = this.userRepository.findById(userId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    return mapper.map(found, UserDto.class);
  }

  public void updateCheckedInUserById(String userId, String newApartmentId) {
    User found = this.userRepository.findById(userId)
      .orElseThrow(RegisteredUserNotFoundException::new);
    Apartment apt = this.apartmentRepository.findById(newApartmentId)
      .orElseThrow(() -> new DataNotFoundFromDBException("유효하지 않은 아파트 ID입니다."));
    found.setCheckedIn(apt);
    this.userRepository.save(found);
  }

}