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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.model.Empinout;
import com.asiczen.api.attendancemgmt.services.EmpinoutServicImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/empinout")
public class EmpInOut {
	
	private static final Logger logger = LoggerFactory.getLogger(EmpInOut.class);

	@Autowired
	private EmpinoutServicImpl empinoutService;
	
	@GetMapping("/empinout")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Empinout> storeLoginLogout(@Valid @RequestBody Empinout empinout) {

		try {
			empinoutService.storeEmpInOut(empinout);
			return ResponseEntity.ok().body(empinout);
		} catch (Exception ep) {
			logger.error("There is an error while capturing the data");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(empinout);
		}
	}
}
