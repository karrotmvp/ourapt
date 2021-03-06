package com.karrotmvp.ourapt.v1.common.eventlistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karrotmvp.ourapt.v1.log.LogService;
import com.karrotmvp.ourapt.v1.log.event.AccessEvent;
import com.karrotmvp.ourapt.v1.log.vo.LogVo;
import io.sentry.Sentry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class LogRecorder {

  private final LogService logService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private final ObjectMapper jsonBuilder = new ObjectMapper();
  private final String[] exclusionPatterns = {
    "/api/v1/app/*",
    "/api/v1/survey",
    "/api/v1/preopen"
  };

  @EventListener(AccessEvent.class)
  public void handleAccessEvent(AccessEvent event) {
    // Print accessLog to stdout
    try {
      logger.info(buildRequestLogToPrint(event));
    } catch (JsonProcessingException e) {
      Sentry.captureException(e);
    }

    if (Arrays.stream(exclusionPatterns).anyMatch((pattern) -> event.getLog().getPath().matches(pattern))) {
      return;
    }

    // Record access log to DB
    this.logService.logEveryRequest(event.getLog(), event.getEventAt());
  }

  private String buildRequestLogToPrint(AccessEvent event) throws JsonProcessingException {
    LogVo log = event.getLog();
    Map<String, String> jsonSource = new HashMap<>();
    jsonSource.put("timestamp", this.dateFormatter.format(event.getEventAt()));
    jsonSource.put("method", log.getMethod());
    jsonSource.put("path", log.getPath());
    jsonSource.put("status", String.valueOf(log.getStatus()));
    jsonSource.put("regionId", log.getRegionId());
    jsonSource.put("userId", log.getUserId());
    return this.jsonBuilder.writeValueAsString(jsonSource);
  }

}
