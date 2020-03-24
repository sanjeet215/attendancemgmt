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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.model.AppliedLeaves;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.ApplyServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ApplyLeaveController {

	private static final Logger logger = LoggerFactory.getLogger(ApplyLeaveController.class);

	@Autowired
	ApplyServiceImpl applyLeaveService;

	/* Employee or Moderator applies Leave */

	@PostMapping("/applyleave")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> postLeaves(@Valid @RequestBody AppliedLeaves leaves) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				"Leaves posted Successfully", applyLeaveService.postLeaves(leaves)));
	}

	/* Get Employee specific Leave with status */
	@GetMapping("/applyleave")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> getLeaves(@Valid @RequestParam String orgid, 
												 @Valid @RequestParam String empId,
												 @Valid @RequestParam String status) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Leaves data extracted successfully", applyLeaveService.getLeaveswithStatus(orgid, empId, status)));
	}

	
	/* Get Leaves with count and status*/
	
	
	
	/*Get Leaves by OrgId. This is for my approval page. It should show all pending Leaves for all employees*/

	@GetMapping("/leave/myapprovals")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getMyApprovals(@Valid @RequestParam String orgid,
													  @Valid @RequestParam String status){
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "Leaves data extracted successfully", 
																		 applyLeaveService.getLeaveswithOrgandStatus(orgid,status)));
	}
	
	
	@GetMapping("/leave/mystatus")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<ApiResponse> getEmpLevelLeaves(@Valid @RequestParam String orgid, 
												 @Valid @RequestParam String empid) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "Leaves data extracted successfully", 
																		 applyLeaveService.getLeaveswithOrgandEmpId(orgid, empid)));
	}
	
}
