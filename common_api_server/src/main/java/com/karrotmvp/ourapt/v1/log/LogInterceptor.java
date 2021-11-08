package com.karrotmvp.ourapt.v1.log;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String instanceId = request.getHeader("Instance-Id");
    String regionId = request.getHeader("Region-Id");
    System.out.println("[InstanceId:" + instanceId + " , RegionId: " + regionId + "]");
    return true;
  }
}
