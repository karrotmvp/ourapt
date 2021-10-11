package com.karrotmvp.ourapt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OuraptApplication {
	public static void main(String[] args) {
		SpringApplication.run(OuraptApplication.class, args);
	}
}
