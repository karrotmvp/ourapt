package com.karrotmvp.ourapt.v1.log;

import com.karrotmvp.ourapt.v1.log.entity.FirstRequestLog;
import com.karrotmvp.ourapt.v1.log.entity.RequestLog;
import com.karrotmvp.ourapt.v1.log.repository.FirstRequestLogRepository;
import com.karrotmvp.ourapt.v1.log.repository.RequestLogRepository;
import com.karrotmvp.ourapt.v1.log.vo.LogVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LogService {
  private final FirstRequestLogRepository firstRequestLogRepository;
  private final RequestLogRepository requestLogRepository;

  public LogService(FirstRequestLogRepository firstRequestLogRepository, RequestLogRepository requestLogRepository) {
    this.firstRequestLogRepository = firstRequestLogRepository;
    this.requestLogRepository = requestLogRepository;
  }

  public void logEveryRequest(LogVo logIngredient, Date timestamp) {
    RequestLog log = new RequestLog();
    log.setPath(logIngredient.getPath());
    log.setMethod(logIngredient.getMethod());
    log.setRegionId(logIngredient.getRegionId());
    log.setUserId(logIngredient.getUserId());
    log.setStatus(logIngredient.getStatus());
    log.setCreatedAt(timestamp);
    this.requestLogRepository.save(log);
  }

  @Transactional
  public void logFirstRequest(Referer referer, String userId) {
    FirstRequestLog firstLog = this.firstRequestLogRepository.findById(userId).orElse(null);
    if (firstLog != null) {
      return;
    }
    this.firstRequestLogRepository.save(new FirstRequestLog(userId, referer != null ? referer : Referer.UNKNOWN));
  }
}
