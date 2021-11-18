package com.karrotmvp.ourapt.v1.user;

import com.karrotmvp.ourapt.v1.apartment.ApartmentRepository;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.common.exception.application.BannedUserException;
import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.RegisteredUserNotFoundException;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
import com.karrotmvp.ourapt.v1.user.entity.User;
import com.karrotmvp.ourapt.v1.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    User found = safelyGetUserById(userId);
    return mapper.map(found, UserDto.class);
  }

  public void updateCheckedInUserById(String userId, String newApartmentId) {
    User found = safelyGetUserById(userId);
    Apartment apt = this.apartmentRepository.findById(newApartmentId)
      .orElseThrow(() -> new DataNotFoundFromDBException("유효하지 않은 아파트 ID입니다."));
    found.setCheckedIn(apt);
    this.userRepository.save(found);
  }

  public void assertUserIsNotBanned(User user) {
    if (user.isBanned()) {
      throw new BannedUserException();
    }
  }

  @Transactional
  public void banUserById(String userId) {
    User target = safelyGetUserById(userId);
    target.setBannedAt(new Date());
    this.userRepository.save(target);
  }

  @Transactional
  public void cancelBanUserById(String userId) {
    User target = safelyGetUserById(userId);
    target.setBannedAt(null);
    this.userRepository.save(target);
  }

  public List<UserDto> getUsersWithPagination(int perPage, int pageNum) {
    return this.userRepository.findAll(PageRequest.of(pageNum, perPage))
      .stream()
      .map(u -> mapper.map(u, UserDto.class))
      .collect(Collectors.toList());
  }

  public int getCountOfAllUsers() {
    return Math.toIntExact(this.userRepository.countByDeletedAtIsNull());
  }

  public User safelyGetUserById(String userId) {
    return this.userRepository.findById(userId)
      .orElseThrow(RegisteredUserNotFoundException::new);
  }


}