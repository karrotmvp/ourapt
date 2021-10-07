package com.karrotmvp.ourapt.v1.auth;

import com.karrotmvp.ourapt.v1.user.UserProfileDto;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class KarrotOAuthProvider {

  public Authentication authenticate(KarrotAuthenticationToken authentication) { // throws AuthenticationException{
  
    UserProfileDto userProfileFromKarrot = getUserProfileFromKarrot(authentication.getCredentials());
    return new KarrotAuthenticationToken(authentication.getCredentials(), userProfileFromKarrot);
  }

  public UserProfileDto getUserProfileFromKarrot(String accessToken) {
    
    UserProfileDto userProfileFromKarrot = new UserProfileDto(); // temp, not implement yet TODO: karrot api call
    return userProfileFromKarrot;
  }
  
}
