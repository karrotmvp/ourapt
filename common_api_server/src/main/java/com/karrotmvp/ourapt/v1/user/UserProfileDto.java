package com.karrotmvp.ourapt.v1.user;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProfileDto {
  
  private String userId;
  
  private String nickname;

  private String profileImageUrl;

  private String phoneNumber;

  public User toEntity() {
    User newUser = new User();
    newUser.setKarrotId(userId);
    newUser.setPushAgreedAt(new Date());
    return newUser;
  }
}
