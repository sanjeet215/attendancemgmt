package com.asiczen.api.attendancemgmt.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PasswordResetRequest {

	@NotBlank
	@NotEmpty(message = "empEmailId is required/Can't be blank")
	@Email
	private String emailId;

	@NotBlank
	@NotEmpty(message = "empPhoneNo is required/Can't be blank")
	@Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Mobile number is invalid")
	private String phoneNo;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return "PasswordResetRequest [emailId=" + emailId + ", phoneNo=" + phoneNo + "]";
	}
	
	
	
}
