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
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.model.Organization;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.OrganizationServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	OrganizationServiceImpl orgService;
	
	/* Create New Organization */
	
	@PostMapping("/org")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> createOrganization(@Valid @RequestBody Organization org){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
																						"Organization Created Successfully",
																						orgService.addOrganization(org)));
	}
	
	/* Get all Organization */	
	@GetMapping("/org")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> getAllOrganizations(){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																				  "Organization Extracted Successfully",
																				  orgService.getAllOrganization()));
	}
	
	/*Update Organization*/
	@PutMapping("/org")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> updateOrganization(@Valid @RequestBody Organization org){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				  											 "Organization Updaed Successfully",
				  											 orgService.updateOrganization(org)));
		
	}
	
}
