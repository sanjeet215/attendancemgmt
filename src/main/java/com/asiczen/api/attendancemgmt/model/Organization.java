package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Organization")
public class Organization extends AuditModel {

	private static final long serialVersionUID = 8345531028140196989L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 5, max = 10, message = "organizationDisplayName should be between 5 to 10 characters")
	@NotEmpty(message = "organizationDisplayName is required/Can't be blank")
	@Column(name = "organizationDisplayName",unique=true)
	@Pattern(regexp = "^[\\S]*$")
	private String organizationDisplayName;

	@Size(min = 5, max = 50, message = "organizationDescription should be between 5 to 50 characters")
	@NotEmpty(message = "organizationDescription is required/Can't be blank")
	private String organizationDescription;

	@Size(min = 2, max = 50, message = "organizationLocation should be between 2 to 50 characters")
	@NotEmpty(message = "organizationLocation is required/Can't be blank")
	private String organizationLocation;

	@Size(min = 5, max = 50, message = "contactPersonName should be between 5 to 50 characters")
	@NotEmpty(message = "contactPersonName is required/Can't be blank")
	private String contactPersonName;

	@NotEmpty(message = "contactEmailId is required/Can't be blank")
	@Size(min = 5, max = 50, message = "contactEmailId should be between 5 to 50 characters")
	@Column(name = "contactEmailId", unique = true, nullable = false, length = 50)
	@Pattern(regexp = "^(.+)@(.+)$", message = "Email Id is invalid")
	@Email
	private String contactEmailId;

	@NotEmpty(message = "PhoneNo is required/Can't be blank")
	@Size(min = 5, max = 10, message = "PhoneNo should be 10 characters")
	@Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "PhoneNo number is invalid")
	@Column(name = "empPhoneNo", unique = true, nullable = false)
	private String organizationcontact;

	@NotNull(message = "status can't be null")
	private String status;

	private String regDate;

	public Organization() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationDisplayName() {
		return organizationDisplayName;
	}

	public void setOrganizationDisplayName(String organizationDisplayName) {
		this.organizationDisplayName = organizationDisplayName;
	}

	public String getOrganizationDescription() {
		return organizationDescription;
	}

	public void setOrganizationDescription(String organizationDescription) {
		this.organizationDescription = organizationDescription;
	}

	public String getOrganizationLocation() {
		return organizationLocation;
	}

	public void setOrganizationLocation(String organizationLocation) {
		this.organizationLocation = organizationLocation;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactEmailId() {
		return contactEmailId;
	}

	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}

	public String getOrganizationcontact() {
		return organizationcontact;
	}

	public void setOrganizationcontact(String organizationcontact) {
		this.organizationcontact = organizationcontact;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", organizationDisplayName=" + organizationDisplayName
				+ ", organizationDescription=" + organizationDescription + ", organizationLocation="
				+ organizationLocation + ", contactPersonName=" + contactPersonName + ", contactEmailId="
				+ contactEmailId + ", organizationcontact=" + organizationcontact + ", status=" + status + ", regDate="
				+ regDate + "]";
	}

}
