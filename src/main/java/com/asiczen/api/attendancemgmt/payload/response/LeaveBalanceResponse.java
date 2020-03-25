package com.asiczen.api.attendancemgmt.payload.response;

import java.util.List;

public class LeaveBalanceResponse {

	private String empId;
	private String orgId;
	private List<LeaveBalance> leaves;

	public LeaveBalanceResponse() {
		super();
	}

	public LeaveBalanceResponse(String empId, String orgId, List<LeaveBalance> leaves) {
		super();
		this.empId = empId;
		this.orgId = orgId;
		this.leaves = leaves;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<LeaveBalance> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<LeaveBalance> leaves) {
		this.leaves = leaves;
	}

	@Override
	public String toString() {
		return "LeaveBalanceResponse [empId=" + empId + ", orgId=" + orgId + ", leaves=" + leaves + "]";
	}

}
