package com.asiczen.api.attendancemgmt.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ChangePasswordRequest {

	@NotBlank
	@NotEmpty(message = "empEmailId is required/Can't be blank")
	@Email
	private String emailId;

	@NotBlank
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message = "Password is invalid,should contain lowercase,uppercase,special character and must be between 6-20 characters")
	private String password;

	public ChangePasswordRequest() {
		super();
	}

	public ChangePasswordRequest(
			@NotBlank @NotEmpty(message = "empEmailId is required/Can't be blank") @Email String emailId,
			@NotBlank @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message = "Email Id is invalid") String password) {
		super();
		this.emailId = emailId;
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ChangePasswordRequest [emailId=" + emailId + ", password=" + password + "]";
	}

	// ( # Start of group
	// (?=.*\d) # must contains one digit from 0-9
	// (?=.*[a-z]) # must contains one lowercase characters
	// (?=.*[A-Z]) # must contains one uppercase characters
	// (?=.*[@#$%]) # must contains one special symbols in the list "@#$%"
	// . # match anything with previous condition checking
	// {6,20} # length at least 6 characters and maximum of 20
	// )

}
