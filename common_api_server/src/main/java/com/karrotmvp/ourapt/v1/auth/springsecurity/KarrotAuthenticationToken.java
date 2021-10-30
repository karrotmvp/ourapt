package com.karrotmvp.ourapt.v1.auth.springsecurity;

import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

public class KarrotAuthenticationToken extends AbstractAuthenticationToken {

  private final String credentials;
  private final KarrotProfile principal;

  public KarrotAuthenticationToken(String authorizationCode) {
    super(null);
    this.credentials = authorizationCode;
    this.principal = null;
    this.setAuthenticated(false);
  }

  public KarrotAuthenticationToken(String bearerToken, KarrotProfile userProfile) {
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
  public KarrotProfile getPrincipal() {
    return this.principal;
  }
  
}
