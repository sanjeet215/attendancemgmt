package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "availedleaves")
public class AvailedLeaves extends AuditModel {

	private static final long serialVersionUID = -7624850374281605381L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Column(name = "org_id", unique = true)
	private String orgId;

	@Size(min = 5, max = 100, message = "Leave Type should be between 5 to 100 characters")
	@NotEmpty(message = "Leave Type is required/Can't be blank")
	private String leaveTypeName;

	@Min(value = 0, message = "The value must be positive")
	private int quantity;

	@NotEmpty(message = "Emp Id is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	String empId;

	public AvailedLeaves() {
		super();
	}

	public AvailedLeaves(long id,
			@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters") @NotEmpty(message = "Organization Id is required/Can't be blank") String orgId,
			@Size(min = 5, max = 100, message = "Leave Type should be between 5 to 100 characters") @NotEmpty(message = "Leave Type is required/Can't be blank") String leaveTypeName,
			@Min(value = 0, message = "The value must be positive") int quantity,
			@NotEmpty(message = "Emp Id is required/Can't be blank") @Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters") String empId) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.leaveTypeName = leaveTypeName;
		this.quantity = quantity;
		this.empId = empId;
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

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "AvailedLeaves [id=" + id + ", orgId=" + orgId + ", leaveTypeName=" + leaveTypeName + ", quantity="
				+ quantity + ", empId=" + empId + "]";
	}

}
