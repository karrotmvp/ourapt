package com.karrotmvp.ourapt.v1.auth;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class KarrotOAuthFilter extends OncePerRequestFilter {

  private KarrotOAuthProvider karrotOAuthProvider;

  public KarrotOAuthFilter(KarrotOAuthProvider karrotOAuthProvider) {
    this.karrotOAuthProvider = karrotOAuthProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
        try {
          String karrotAccessToken = extractBearerToken(request);
          KarrotAuthenticationToken authentication = new KarrotAuthenticationToken(karrotAccessToken);
          Authentication authenticationResult = karrotOAuthProvider.authenticate(authentication);
          SecurityContextHolder.getContext().setAuthentication(authenticationResult);
          filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
          System.out.println(e.getMessage());
          SecurityContextHolder.clearContext();
          response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
  }

  private String extractBearerToken(HttpServletRequest request) throws AuthenticationException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new AuthenticationServiceException("Request doesn't have Bearer token in Authorization field");      
    }

    return authHeader;
  }
  
  

  
}
