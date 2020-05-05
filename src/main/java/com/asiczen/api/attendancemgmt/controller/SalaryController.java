package com.asiczen.api.attendancemgmt.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.payload.request.SalaryComponent;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.PaymentDetailServices;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SalaryController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);
	
	@Autowired
	PaymentDetailServices service;
	
	@PostMapping("/paydetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> postEmpSalary(@Valid @RequestBody SalaryComponent component){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
																			  "Components created Successfully",
																			  service.updateEmployeeSalary(component)));
	}
	
	
	@GetMapping("/paydetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getEmpSalary(@Valid @RequestParam String orgId,
													@Valid @RequestParam String empId){
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				  															  "Components created Successfully",
				  															  service.getEmployeeSalary(orgId, empId)));
	}
	
	
	@PutMapping("/paydetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateEmpSalary(@Valid @RequestBody SalaryComponent component){
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				  														"Components updated Successfully",
				  														service.updateEmployeeSalary(component)));
	}
	
	
	
}
