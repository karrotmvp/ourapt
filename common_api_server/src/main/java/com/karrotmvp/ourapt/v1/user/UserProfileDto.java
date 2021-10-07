package com.karrotmvp.ourapt.v1.user;

import java.util.Date;

import lombok.Getter;

@Getter
public class UserProfileDto {
  
  private String karrotId;
  
  private String nickname;

  private String profileImageUrl;

  private String phoneNumber;

  public User toEntity() {
    User newUser = new User();
    newUser.setKarrotId(karrotId);
    newUser.setPushAgreedAt(new Date());
    return newUser;
  }
}
