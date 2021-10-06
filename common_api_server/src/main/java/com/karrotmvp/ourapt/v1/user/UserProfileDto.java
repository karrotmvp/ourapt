package com.karrotmvp.ourapt.v1.user;

import lombok.Getter;

@Getter
public class UserProfileDto {
  
  private String karrotId;
  
  private String nickname;

  private String profileImageUrl;

  private String phoneNumber;

  public User toEntity() {
    return new User();
  }
}
