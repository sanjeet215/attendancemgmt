package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "moderator")
public class Moderator extends AuditModel {

	private static final long serialVersionUID = -7088417743728679314L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Column(name = "orgid")
	String orgId;

	@NotEmpty(message = "empEmailId is required/Can't be blank")
	@Size(min = 5, max = 50, message = "empEmailId should be between 5 to 50 characters")
	@Column(name = "empEmailId", unique = true, nullable = false, length = 50)
	@Pattern(regexp = "^(.+)@(.+)$", message = "Email Id is invalid")
	@Email
	String emailId;

	@NotBlank
	@Size(max = 20)
	String username;
	
	boolean status;
	
	
	public Moderator(
			@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters") @NotEmpty(message = "Organization Id is required/Can't be blank") String orgId,
			@NotEmpty(message = "empEmailId is required/Can't be blank") @Size(min = 5, max = 50, message = "empEmailId should be between 5 to 50 characters") @Pattern(regexp = "^(.+)@(.+)$", message = "Email Id is invalid") @Email String emailId,
			@NotBlank @Size(max = 20) String username, @NotBlank boolean status) {
		super();
		this.orgId = orgId;
		this.emailId = emailId;
		this.username = username;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Moderator [id=" + id + ", orgId=" + orgId + ", emailId=" + emailId + ", username=" + username + "]";
	}

}
