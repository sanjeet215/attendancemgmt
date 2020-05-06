package com.asiczen.api.attendancemgmt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/*Batch Service to populate working hours*/

@Configuration
public class BatchServices {

	private static final Logger logger = LoggerFactory.getLogger(BatchServices.class);
	
	@Autowired
	CalulateWorkingHours calcService;

	@Scheduled(fixedDelay = 1000000000)
	public void calculateworkinghours() {
		logger.info("Batch exection started");
		
		calcService.exactworkinghours();
		
		logger.info("Batch exection ended");
	}
}
