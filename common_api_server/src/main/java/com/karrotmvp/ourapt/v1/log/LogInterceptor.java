package com.karrotmvp.ourapt.v1.log;

import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotAuthenticationToken;
import com.karrotmvp.ourapt.v1.common.exception.application.RequestHeaderNotFoundException;
import com.karrotmvp.ourapt.v1.log.event.AccessEventPublisher;
import com.karrotmvp.ourapt.v1.log.vo.LogVo;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final AccessEventPublisher logEventPublisher;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    assertRegionIdIncludedInHeader(request);
    return true;
  }


  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    Authentication auth = getAuthenticationFromSecurity();
    this.logEventPublisher.publish(
      LogVo.builder()
        .method(request.getMethod())
        .path(extractPath(request))
        .regionId(extractRegionId(request).orElse(null))
        .userId(parseUserId(auth).orElse(null))
        .status(response.getStatus())
        .build()
    );
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
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
  private void assertRegionIdIncludedInHeader(HttpServletRequest request) {
    extractRegionId(request)
      .orElseThrow(() -> new RequestHeaderNotFoundException("There is no Region-Id in request headers"));
  }

  private Optional<String> extractRegionId(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("Region-Id"));
  }

  private String extractPath(HttpServletRequest request) {
    return request.getRequestURI();
  }

}
