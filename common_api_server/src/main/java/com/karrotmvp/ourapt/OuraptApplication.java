package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.common.Static;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OuraptApplication {

	@PostConstruct
	public void setTimezone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		Logger logger = LoggerFactory.getLogger(this.getClass());
    Date now = new Date();
    setServerStartTime(now);
		logger.info("Server started at - " + now.toString());
	}
	public static void main(String[] args) {
		SpringApplication.run(OuraptApplication.class, args);
	}

  private void setServerStartTime(Date startDate) {
    Static.serverStartTime = startDate;
  }
    
}
