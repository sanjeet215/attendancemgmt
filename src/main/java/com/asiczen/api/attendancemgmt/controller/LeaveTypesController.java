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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.model.LeaveTypes;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.LeaveTypesServiceImple;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class LeaveTypesController {

	private static final Logger logger = LoggerFactory.getLogger(LeaveTypesController.class);
	
	@Autowired
	LeaveTypesServiceImple leaveTypeService;
	
	@PostMapping("/leavetype")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> createLeaveTypes(@Valid @RequestBody LeaveTypes leaveTypes ){
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
																"Organization Created Successfully",
																leaveTypeService.postLeaves(leaveTypes)));
	}
	
//	@GetMapping("/leavetype")
//	public ResponseEntity<ApiResponse> getAllLeaveTypes(){
//		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
//														 "LeaveTypes Extracted Successfully",
//														 leaveTypeService.getAllLeavetypes()));
//	}
	
	
	@GetMapping("/leavetype")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getLeaveTypesByOrgId(@Valid @RequestParam String orgid){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				 														"LeaveTypes Extracted Successfully",
				 														leaveTypeService.getLeaveTypesByOrganization(orgid)));
	}
	
}
