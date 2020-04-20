package com.asiczen.api.attendancemgmt.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRoleChangeRequest {

	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	private String orgId;

	@NotBlank
	@Size(max = 50, message = "Email id length must be less than 50")
	@Email
	private String email;

	private Set<String> role;

	public UserRoleChangeRequest() {
		super();
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	@Override
	public String toString() {
		return "UserRoleChangeRequest [orgId=" + orgId + ", email=" + email + ", role=" + role + "]";
	}

}
