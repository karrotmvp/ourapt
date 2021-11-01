package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.apartment.ApartmentService;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import com.karrotmvp.ourapt.v1.user.UserController;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
import com.karrotmvp.ourapt.v1.user.dto.request.ChangeMyCheckedInDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTest {
  @Autowired
  private UserController userController;

  @Autowired
  private UserService userService;

  @Autowired
  private ApartmentService apartmentService;

  @Test
  void getMyInfoTest() {
    UserDto adminUser = getAdminUserDto();
    UserDto result = userController.getMyInfo(adminUser.getProfile()).getData();
    assertEquals(adminUser.getId(), result.getId());
    assertEquals(adminUser.getProfile().getId(), result.getProfile().getId());
  }

  @Test
  void changeMyCheckedInTest() {
    UserDto adminUser = getAdminUserDto();
    ApartmentDto testApt = getTestApartment();
    CommonResponseBody<Void> resBody = userController.changeMyCheckedIn(
      adminUser.getProfile(),
      new ChangeMyCheckedInDto(testApt.getId()));
    assertEquals(ApiResult.SUCCESS.getResult(), resBody.getStatus().getResult());

  }

  private UserDto getAdminUserDto() {
    return this.userService.getUserById("ADMIN");
  }

  private ApartmentDto getTestApartment() {
    return this.apartmentService.getApartmentById("TEST");
  }


}

