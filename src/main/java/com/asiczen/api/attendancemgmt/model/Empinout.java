package com.asiczen.api.attendancemgmt.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "empinout")
public class Empinout extends AuditModel {

	private static final long serialVersionUID = -5758884115578900226L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	private String orgId;

	@NotEmpty(message = "Emp Id is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	private String empId;

	private Date timeStamp;

	@NotEmpty(message = "type is required/Can't be blank")
	@Size(min = 2, max = 3, message = "type should be between 2 to 3 characters")
	private String type;

	public Empinout() {
		super();
	}

	public Empinout(
			@NotEmpty(message = "Organization Id is required/Can't be blank") @Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters") String orgId,
			@NotEmpty(message = "Emp Id is required/Can't be blank") @Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters") String empId,
			Date timeStamp,
			@NotEmpty(message = "type is required/Can't be blank") @Size(min = 2, max = 3, message = "type should be between 2 to 3 characters") String type) {
		super();
		this.orgId = orgId;
		this.empId = empId;
		this.timeStamp = timeStamp;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Empinout [id=" + id + ", orgId=" + orgId + ", empId=" + empId + ", timeStamp=" + timeStamp + ", type="
				+ type + "]";
	}

}
