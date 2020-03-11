package com.asiczen.api.attendancemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.asiczen.api.attendancemgmt.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
@EnableJpaAuditing
@EnableAsync
public class AttendancemgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendancemgmtApplication.class, args);
	}

}
