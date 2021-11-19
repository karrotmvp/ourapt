package com.karrotmvp.ourapt.v1.log.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LogVo {
  private String path;
  private String method;
  private String regionId;
  private String userId;
  private int status;
}
