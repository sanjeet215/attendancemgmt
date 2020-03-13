package com.asiczen.api.attendancemgmt.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiczen.api.attendancemgmt.payload.request.ChangePasswordRequest;
import com.asiczen.api.attendancemgmt.payload.request.PasswordResetRequest;
import com.asiczen.api.attendancemgmt.payload.response.ApiResponse;
import com.asiczen.api.attendancemgmt.services.ForgotPasswordServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	ForgotPasswordServiceImpl forgotpwdService;

	/* Forgot password post Request */
	@PostMapping("/password")
	public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody PasswordResetRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(),
				"Password Rest SuccessFul", forgotpwdService.passwordReset(request)));
	}

	@PutMapping("/password")
	public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse(HttpStatus.ACCEPTED.value(),
				"Password Changed Successfully", forgotpwdService.changePassword(request)));
	}
}
