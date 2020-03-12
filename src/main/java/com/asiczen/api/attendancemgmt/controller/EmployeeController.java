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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.EmpServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmpServiceImpl empService;
	
	/* Create New Employee */
	
	@PostMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> createEmployee(@Valid @RequestBody Employee emp){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
																					   "Employee Created Successfully",
																					   empService.addNewEmployee(emp)));
	}
	
	
	/* Get all Employees */	
	@GetMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getAllEmployees(){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																				  "Organization Created Successfully",
																				  empService.getAllEmployees()));
	}
	
	/*Update Employee*/
	@PutMapping("/emp")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateEmployee(@Valid @RequestBody Employee emp){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				  											 "Organization Created Successfully",
				  											empService.updateEmployee(emp)));
		
	}
	
	
	/* Get Employee By Id */
	
	@GetMapping("/emp/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> retrieveEmp(@Valid @PathVariable long id){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "Employee with id: "+id+" retrieved successfully",
																		  empService.getEmployeeById(id)));
	}
	
	/*Active Users*/
	@GetMapping("/emp/count")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('user')")
	public ResponseEntity<ApiResponse> countEmpByOrganization(@Valid @RequestParam String orgid){
		logger.debug("Incoming Organization id: "+orgid);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
														   				 "Employee Count extracted",
														   				 empService.countEmployeebyOrganization(orgid, true)));
	}
	
}
