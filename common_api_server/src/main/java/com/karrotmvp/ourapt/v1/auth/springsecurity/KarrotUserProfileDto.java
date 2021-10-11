package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karrotmvp.ourapt.v1.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KarrotUserProfileDto {

  @JsonProperty("user_id")
  private String userId;

  @JsonProperty("nickname")
  private String nickname;

  public User toEntity() {
    User newUser = new User();
    newUser.setKarrotId(userId);
    return newUser;
  }
}
