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

import com.asiczen.api.attendancemgmt.model.Department;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.DeptServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	DeptServiceImpl deptService;
	
	/*Create/Add Department*/
	@PostMapping("/dept")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> createDepartment(@Valid @RequestBody Department dept){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
																			  "Department Created Successfully",
																			  deptService.addDepartment(dept)));
		
	}
	
	/*Update/Deactivate Department*/
	
	@PutMapping("/dept")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateDepartment(@Valid @RequestBody Department dept){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
					 										 "Department Updaed Successfully",
					 										deptService.updateDepartment(dept)));
	}
	
	
	/*Get all Departments*/
	@GetMapping("/dept")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getAllDepartments() {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "Department Updaed Successfully", 
																		 deptService.getAllDepartments()));
	}
	
	@GetMapping("/deptbyOrg")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getDepartmentsbyOrg(@Valid @RequestParam String orgId) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "Org wise Department Extracted", 
																		 deptService.getDepartmentsByOrganization(orgId)));
	}
	
	
	@GetMapping("/dept/count")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getDepartmentCount(@Valid @RequestParam String orgid){
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		"Department Count extracted",
																		deptService.countDepartmentbyOrg(orgid)));
	}
}
