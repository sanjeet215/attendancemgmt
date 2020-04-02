package com.asiczen.api.attendancemgmt.payload.response;

import java.util.List;

public class EmployeeByOrgResponse {

	String orgId;
	List<String> empId;

	public EmployeeByOrgResponse() {
		super();
	}

	public EmployeeByOrgResponse(String orgId, List<String> empId) {
		super();
		this.orgId = orgId;
		this.empId = empId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<String> getEmpId() {
		return empId;
	}

	public void setEmpId(List<String> empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "EmployeeByOrgResponse [orgId=" + orgId + ", empId=" + empId + "]";
	}

}
