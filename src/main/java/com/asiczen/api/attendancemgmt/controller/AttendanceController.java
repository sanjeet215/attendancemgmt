package com.asiczen.api.attendancemgmt.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.DeviceWorkingHoursService;
import com.asiczen.api.attendancemgmt.services.EmpinoutServicImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AttendanceController {

	private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

	@Autowired
	EmpinoutServicImpl empService;

	@Autowired
	DeviceWorkingHoursService deviceService;

	/* Data from android Device */
	@GetMapping("/swipein")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> getSwipeInSwipeout(@Valid @RequestParam String orgId,
			@Valid @RequestParam String empId) {

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Swipein and Swipe Out details are as below", empService.getEmpLoginDetails(orgId, empId)));
	}

	@GetMapping("/workinghours")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> getDeviceWorkinghours(@Valid @RequestParam String orgId,
			@Valid @RequestParam String empId, @Valid @RequestParam(required = false) String month,
			@Valid @RequestParam(required = false) String year) {
		
		/*  write validation for month and year */
		
		if(month.isEmpty() || year.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
					"working hours extracted for Employee " + empId, deviceService.getworkinghoursperDevice(orgId, empId)));
		} else {
			
			deviceService.compareAttendanceData(orgId, empId, month, year);
			
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
					"working hours extracted for Employee " + empId, deviceService.getworkinghoursperDevicebyMonthandYear(orgId, empId, month, year)));
		}
		
		
	}
	
	
	@GetMapping("/recondata")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> generateReconData(@Valid @RequestParam String orgId,
														 @Valid @RequestParam String empId,
														 @Valid @RequestParam String month,
														 @Valid @RequestParam String year){
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
															             "ReconData extracted successfully",
															             deviceService.compareAttendanceData(orgId, empId, month, year)));
	}
	
	
}
