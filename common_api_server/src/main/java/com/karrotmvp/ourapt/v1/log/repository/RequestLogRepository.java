package com.karrotmvp.ourapt.v1.log.repository;

import com.karrotmvp.ourapt.v1.log.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
