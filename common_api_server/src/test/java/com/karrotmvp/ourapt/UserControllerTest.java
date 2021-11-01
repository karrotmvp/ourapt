package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.user.UserController;
import com.karrotmvp.ourapt.v1.user.UserService;
import com.karrotmvp.ourapt.v1.user.dto.UserDto;
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

  @Test
  void getMyInfoTest() {
    UserDto adminUser = this.userService.getUserByUserId("ADMIN");
    UserDto result = userController.getMyInfo(adminUser.getProfile()).getData();
    assertEquals(adminUser.getId(),result.getId());;
    assertEquals(adminUser.getProfile().getId(), result.getProfile().getId());
  }


}

