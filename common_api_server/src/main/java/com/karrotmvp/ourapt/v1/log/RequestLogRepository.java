package com.karrotmvp.ourapt.v1.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
