package com.asiczen.api.attendancemgmt.payload.response;

import java.util.List;

public class LockResponse {

	private String orgId;
	private String empId;
	private String emailId;
	private List<String> lockmacid;

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<String> getLockmacid() {
		return lockmacid;
	}

	public void setLockmacid(List<String> lockmacid) {
		this.lockmacid = lockmacid;
	}

	@Override
	public String toString() {
		return "LockResponse [orgId=" + orgId + ", empId=" + empId + ", emailId=" + emailId + ", lockmacid=" + lockmacid
				+ "]";
	}

}
