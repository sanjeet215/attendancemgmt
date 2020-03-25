package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
//@Table(name = "leavetypes", uniqueConstraints = @UniqueConstraint(columnNames = { "org_id", "leaveTypeName" }))
@Table(name = "leavetypes")
public class LeaveTypes extends AuditModel {

	private static final long serialVersionUID = -1040317321318112432L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Column(name = "orgid")
	private String orgId;

	@Size(min = 5, max = 100, message = "Leave Type should be between 5 to 100 characters")
	@NotEmpty(message = "Leave Type is required/Can't be blank")
	@Column(unique = false)
	private String leaveTypeName;

	@Min(value = 0, message = "The value must be positive")
	private int quantity;

	@NotNull(message = "status can't be null")
	private boolean status;

	public LeaveTypes() {
		super();
	}

	public LeaveTypes(long id,
			@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters") @NotEmpty(message = "Organization Id is required/Can't be blank") String orgId,
			@Size(min = 5, max = 100, message = "Leave Type should be between 5 to 100 characters") @NotEmpty(message = "Leave Type is required/Can't be blank") String leaveTypeName,
			@Min(value = 0, message = "The value must be positive") int quantity,
			@NotNull(message = "status can't be null") boolean status) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.leaveTypeName = leaveTypeName;
		this.quantity = quantity;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LeaveTypes [id=" + id + ", orgId=" + orgId + ", leaveTypeName=" + leaveTypeName + ", quantity="
				+ quantity + ", status=" + status + "]";
	}

}
