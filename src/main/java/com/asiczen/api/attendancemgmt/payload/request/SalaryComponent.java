package com.asiczen.api.attendancemgmt.payload.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SalaryComponent {

	@NotEmpty(message = "orgid is required/Can't be blank")
	@Size(min = 5, max = 10, message = "orgid should be between 5 to 10 characters")
	String orgId;

	@NotEmpty(message = "empId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	String empId;

	@NotEmpty
	List<Component> components;

	public SalaryComponent() {
		super();
	}

	public SalaryComponent(
			@NotEmpty(message = "orgid is required/Can't be blank") @Size(min = 5, max = 10, message = "orgid should be between 5 to 10 characters") String orgId,
			@NotEmpty(message = "empId is required/Can't be blank") @Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters") String empId,
			@NotEmpty List<Component> components) {
		super();
		this.orgId = orgId;
		this.empId = empId;
		this.components = components;
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

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		return "SalaryComponent [orgId=" + orgId + ", empId=" + empId + ", components=" + components + "]";
	}

}
