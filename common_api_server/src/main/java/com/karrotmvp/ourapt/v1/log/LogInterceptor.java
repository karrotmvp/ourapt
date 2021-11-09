package com.karrotmvp.ourapt.v1.log;

import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotAuthenticationToken;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class LogInterceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final LogService logService;

  public LogInterceptor(LogService logService) {
    this.logService = logService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    Authentication auth = getAuthenticationFromSecurity();
    logger.info(
      "[InstanceId:" + request.getHeader("Instance-Id") +
      ", RegionId: " + request.getHeader("Region-Id") +
        ", AccessToken: " + request.getHeader("Authorization") + "]");
    this.logService.logEveryRequest(request, parseUserId(auth).orElse(null));
    return true;
  }

  private Authentication getAuthenticationFromSecurity() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
  private Optional<String> parseUserId(Authentication auth) {
    if (!KarrotAuthenticationToken.class.isAssignableFrom(auth.getClass())) {
      return Optional.empty();
    }
    return Optional.of(((KarrotProfile) auth.getPrincipal()).getId());
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    logger.info(request.getMethod() + " " + request.getRequestURI() + " " + response.getStatus());
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }
}
