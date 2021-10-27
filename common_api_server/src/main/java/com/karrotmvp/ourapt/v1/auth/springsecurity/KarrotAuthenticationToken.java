package com.karrotmvp.ourapt.v1.auth.springsecurity;

import java.util.Arrays;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class KarrotAuthenticationToken extends AbstractAuthenticationToken {

  private final String credentials;
  private final KarrotOpenApiUserDto principal;

  public KarrotAuthenticationToken(String authorizationCode) {
    super(null);
    this.credentials = authorizationCode;
    this.principal = null;
    this.setAuthenticated(false);
  }

  public KarrotAuthenticationToken(String bearerToken, KarrotOpenApiUserDto userProfile) {
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
  public KarrotOpenApiUserDto getPrincipal() {
    return this.principal;
  }
  
}
