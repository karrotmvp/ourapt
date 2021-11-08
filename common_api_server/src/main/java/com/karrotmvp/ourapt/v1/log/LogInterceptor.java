package com.karrotmvp.ourapt.v1.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String instanceId = request.getHeader("Instance-Id");
    String regionId = request.getHeader("Region-Id");
    String authHeader = request.getHeader("Authorization");
    logger.info("[InstanceId:" + instanceId + ", RegionId: " + regionId + ", AccessToken: " + authHeader + "]");
    return true;
  }
}
