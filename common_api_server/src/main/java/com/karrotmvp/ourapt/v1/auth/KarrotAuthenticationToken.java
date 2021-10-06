package com.karrotmvp.ourapt.v1.auth;

import java.util.Arrays;

import com.karrotmvp.ourapt.v1.user.UserProfileDto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class KarrotAuthenticationToken extends AbstractAuthenticationToken {

  private final String credentials;
  private final UserProfileDto principal;

  public KarrotAuthenticationToken(String bearerToken) {
    super(null);
    this.credentials = bearerToken;
    this.principal = null;
    this.setAuthenticated(false);
  }

  public KarrotAuthenticationToken(String bearerToken, UserProfileDto userProfile) {
    super(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    this.credentials = bearerToken;
    this.principal = userProfile;
    this.setAuthenticated(true);
  }

  @Override
  public String getCredentials() {
    return this.credentials;
  }

  @Override
  public UserProfileDto getPrincipal() {
    return this.principal;
  }
  
}
