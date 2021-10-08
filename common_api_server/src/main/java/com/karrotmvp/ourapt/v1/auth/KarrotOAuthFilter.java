package com.karrotmvp.ourapt.v1.auth;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class KarrotOAuthFilter extends GenericFilterBean {

  private final KarrotOAuthProvider karrotOAuthProvider;

  public KarrotOAuthFilter(KarrotOAuthProvider provider) {
    this.karrotOAuthProvider = provider;
  }

  private String extractBearerToken(HttpServletRequest request) throws AuthenticationServiceException {
    String authHeader = request.getHeader("Authorization");
    // TODO: Refactor, 에러전파
    if (authHeader == null) {
      String msg = "Fail to find 'Authorization' header from request.";
      throw new AuthenticationServiceException(msg);
    }
    if (!authHeader.startsWith("Bearer ")) {
      String msg = "Authorization header has invalid BearerToken.";
      throw new AuthenticationServiceException(msg);
    }
    return authHeader;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    // generate unauthenticated token.
    // Later, this token is validated by KarrotOAuthProvider.
    String karrotAccessToken = extractBearerToken((HttpServletRequest) request);
    KarrotAuthenticationToken unAuthorizedAuthentication = new KarrotAuthenticationToken(karrotAccessToken);
    SecurityContextHolder.getContext().setAuthentication(unAuthorizedAuthentication);
    chain.doFilter(request, response);
  }
}
