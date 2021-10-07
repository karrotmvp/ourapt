package com.karrotmvp.ourapt.v1.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class KarrotOAuthFilter extends GenericFilterBean {

  private KarrotOAuthProvider karrotOAuthProvider;

  private Logger logger = LoggerFactory.getLogger(getClass());

  public KarrotOAuthFilter(KarrotOAuthProvider karrotOAuthProvider) {
    this.karrotOAuthProvider = karrotOAuthProvider;
  }

  // @Override
  // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
  //     throws ServletException, IOException {
  //       try {
  //         String karrotAccessToken = extractBearerToken(request);
  //         KarrotAuthenticationToken authentication = new KarrotAuthenticationToken(karrotAccessToken);
  //         Authentication authenticationResult = karrotOAuthProvider.authenticate(authentication);
  //         SecurityContextHolder.getContext().setAuthentication(authenticationResult);
  //         filterChain.doFilter(request, response);
  //       } catch (AuthenticationException e) {
  //         logger.info(e.getMessage());
  //         SecurityContextHolder.clearContext();
  //         response.sendError(HttpStatus.UNAUTHORIZED.value());
  //       }
  //     }

  private String extractBearerToken(HttpServletRequest request) throws AuthenticationServiceException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null) {
      throw new AuthenticationServiceException("Fail to find 'Authorization' header from request.");
    }
    if (!authHeader.startsWith("Bearer ")) {
      throw new AuthenticationServiceException("Authorization header has invalid BearerToken.");
    }
    return authHeader;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // try {
      String karrotAccessToken = extractBearerToken((HttpServletRequest) request);
      KarrotAuthenticationToken authentication = new KarrotAuthenticationToken(karrotAccessToken);
      Authentication authenticationResult = karrotOAuthProvider.authenticate(authentication);
      SecurityContextHolder.getContext().setAuthentication(authenticationResult);
    // } catch (AuthenticationException e) {
    //   logger.info(e.getMessage());
    // } finally {
    //   chain.doFilter(request, response, chain);
    // }
    // logger.info(e.getMessage());
    // SecurityContextHolder.clearContext();
    // response.sendError(HttpStatus.UNAUTHORIZED.value());
  }
  
  

  
}
