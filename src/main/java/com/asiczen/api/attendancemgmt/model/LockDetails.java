package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "lockdetails")
public class LockDetails extends AuditModel {

	private static final long serialVersionUID = -2883136547275343915L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NotEmpty(message = "mac id can't be blank")
	String lockmacid;

	@NotEmpty(message = "org id can't be blank")
	String orgId;

	@NotEmpty(message = "emp id can't be blank")
	String empId;

	@Email
	String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLockmacid() {
		return lockmacid;
	}

	public void setLockmacid(String lockmacid) {
		this.lockmacid = lockmacid;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "LockDetails [id=" + id + ", lockmacid=" + lockmacid + ", orgId=" + orgId + ", empId=" + empId
				+ ", email=" + email + "]";
	}

}
