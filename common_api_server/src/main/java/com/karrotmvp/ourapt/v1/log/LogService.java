package com.karrotmvp.ourapt.v1.log;

import com.karrotmvp.ourapt.v1.common.exception.application.RequestHeaderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class LogService {
  private final FirstRequestLogRepository firstRequestLogRepository;
  private final RequestLogRepository requestLogRepository;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public LogService(FirstRequestLogRepository firstRequestLogRepository, RequestLogRepository requestLogRepository) {
    this.firstRequestLogRepository = firstRequestLogRepository;
    this.requestLogRepository = requestLogRepository;
  }

  public void logEveryRequest(HttpServletRequest request, String userId) {
    RequestLog log = new RequestLog();
    log.setInstanceId(this.extractInstanceId(request));
    log.setMethod(request.getMethod());
    log.setRegionId(this.extractRegionId(request));
    log.setPath(this.extractPath(request));
    log.setUserId(userId);
    this.requestLogRepository.save(log);
  }

  private String extractInstanceId(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("Instance-Id"))
      .orElseThrow(() -> new RequestHeaderNotFoundException("There is no Instance-Id in request headers"));
  }

  private String extractRegionId(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader("Region-Id"))
      .orElseThrow(() -> new RequestHeaderNotFoundException("There is no Region-Id in request headers"));
  }

  private String extractPath(HttpServletRequest request) {
    return request.getRequestURI();
  }
}
