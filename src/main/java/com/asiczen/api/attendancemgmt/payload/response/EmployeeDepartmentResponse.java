package com.asiczen.api.attendancemgmt.payload.response;

import java.util.List;

public class EmployeeDepartmentResponse {

	private String orgId;
	private List<String> employeeList;
	private List<String> departmentList;

	public EmployeeDepartmentResponse() {
		super();
	}

	public EmployeeDepartmentResponse(String orgId, List<String> employeeList, List<String> departmentList) {
		super();
		this.orgId = orgId;
		this.employeeList = employeeList;
		this.departmentList = departmentList;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<String> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<String> employeeList) {
		this.employeeList = employeeList;
	}

	public List<String> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<String> departmentList) {
		this.departmentList = departmentList;
	}

	@Override
	public String toString() {
		return "EmployeeDepartmentResponse [orgId=" + orgId + ", employeeList=" + employeeList + ", departmentList="
				+ departmentList + "]";
	}

}
