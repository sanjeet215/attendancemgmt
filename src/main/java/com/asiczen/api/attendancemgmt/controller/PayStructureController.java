package com.asiczen.api.attendancemgmt.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.model.PayStructure;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.PayStructureServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PayStructureController {
	
	private static final Logger logger = LoggerFactory.getLogger(PayStructureController.class);

	@Autowired
	PayStructureServiceImpl payStructure;
	
	
	@PostMapping("/paystructure")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> postComponents(@Valid @RequestBody List<PayStructure> components){
		
		//fix-item later
		String orgid = components.get(0).getOrgId();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(),
				  															  "Components created Successfully",
				  															  payStructure.addSalaryComponents(components, orgid)));
		
	}
	
	@PutMapping("/paystructure")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> updateComponents(@Valid @RequestBody List<PayStructure> components){
		
		//fix-item later
		String orgid = components.get(0).getOrgId();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				  															  "Components created Successfully",
				  															  payStructure.updateComponents(components, orgid)));
		
	}
	
	
	@GetMapping("/paystructure")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> getAllComponents(@Valid @RequestParam String orgId){
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
																		 "Components extracted successfully",
																		 payStructure.getSalaryComponentsByOrganization(orgId)));
		
	}
	

	@DeleteMapping("/paystructure")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<ApiResponse> deleteAllComponents(@Valid @RequestBody PayStructure component){
		
		payStructure.deleteComponent(component, component.getOrgId());
	
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				 														"Components deleted successfully",
				 														"Object deleted successfully"));
	}
	
}
