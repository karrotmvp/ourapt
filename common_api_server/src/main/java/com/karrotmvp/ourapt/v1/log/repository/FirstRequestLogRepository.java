package com.karrotmvp.ourapt.v1.log.repository;

import com.karrotmvp.ourapt.v1.log.entity.FirstRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstRequestLogRepository extends JpaRepository<FirstRequestLog, String> {
}
