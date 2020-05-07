package com.asiczen.api.attendancemgmt.payload.request;

import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20, message = "username should be between 3 to 20 characters")
	private String username;

	@NotBlank
	@Size(min = 3, max = 50, message = "Email id length must be between 3 to 50 characters")
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40, message = "password can't be blank, must be between 6 to 40 chars")
	private String password;

	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	private String orgId;

	@NotEmpty(message = "empId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	@Column(name = "empid", unique = true, nullable = false)
	private String empId;

	@NotEmpty(message = "empPhoneNo is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empPhoneNo should be between 5 to 10 characters")
	@Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Mobile number is invalid")
	@Column(name = "empPhoneNo", unique = true, nullable = false)
	private String phoneNo;

	public SignupRequest() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return "SignupRequest [username=" + username + ", email=" + email + ", role=" + role + ", password=" + password
				+ ", orgId=" + orgId + ", empId=" + empId + ", phoneNo=" + phoneNo + "]";
	}

}
