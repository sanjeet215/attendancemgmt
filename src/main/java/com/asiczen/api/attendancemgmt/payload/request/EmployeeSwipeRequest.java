package com.asiczen.api.attendancemgmt.payload.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmployeeSwipeRequest {

	@NotEmpty(message = "orgId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	private String orgId;

	@NotEmpty(message = "empId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	private String empId;

	@NotEmpty(message = "swipeDate required/Can't be blank")
	@Pattern(regexp = "([0-9]{4})-([0-9]{2})-([0-9]{2})", message = "Date format should be yyyy-mm-dd")
	private String swipeDate;

	@NotEmpty(message = "swipeTime required/Can't be blank")
	@Pattern(regexp = "(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)", message = "time should be in 24 hours, hh:mm:ss")
	private String swipeTime;

	@NotEmpty(message = "type is required/Can't be blank")
	@Size(min = 2, max = 3, message = "type should be between 2 to 3 characters")
	private String type;

	public EmployeeSwipeRequest() {
		super();
	}

	public EmployeeSwipeRequest(
			@NotEmpty(message = "Organization Id is required/Can't be blank") @Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters") String orgId,
			@NotEmpty(message = "Emp Id is required/Can't be blank") @Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters") String empId,
			@Pattern(regexp = "([0-9]{2})/([0-9]{2})/([0-9]{4})", message = "Date format should be dd/mm/yyyy") String swipeDate,
			@Pattern(regexp = "(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)", message = "time should be in 24 hours, hh:mm:ss") String swipeTime,
			@NotEmpty(message = "type is required/Can't be blank") @Size(min = 2, max = 3, message = "type should be between 2 to 3 characters") String type) {
		super();
		this.orgId = orgId;
		this.empId = empId;
		this.swipeDate = swipeDate;
		this.swipeTime = swipeTime;
		this.type = type;
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

	public String getSwipeDate() {
		return swipeDate;
	}

	public void setSwipeDate(String swipeDate) {
		this.swipeDate = swipeDate;
	}

	public String getSwipeTime() {
		return swipeTime;
	}

	public void setSwipeTime(String swipeTime) {
		this.swipeTime = swipeTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "EmployeeSwipeRequest [orgId=" + orgId + ", empId=" + empId + ", swipeDate=" + swipeDate + ", swipeTime="
				+ swipeTime + ", type=" + type + "]";
	}

}
