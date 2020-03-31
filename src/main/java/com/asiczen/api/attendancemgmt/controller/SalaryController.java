package com.asiczen.api.attendancemgmt.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.payload.request.SalaryComponent;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SalaryController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);
	
	//@Autowired
	
	@PostMapping("/paydetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> postEmpSalary(@Valid @RequestBody SalaryComponent component){
		
		return null;
	}
	
	
	@GetMapping("/paydetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getEmpSalary(@Valid @RequestBody SalaryComponent component){
		
		return null;
	}
	
	
	@PutMapping("/paydetails")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateEmpSalary(@Valid @RequestBody SalaryComponent component){
		
		return null;
	}
	
	
	
}
