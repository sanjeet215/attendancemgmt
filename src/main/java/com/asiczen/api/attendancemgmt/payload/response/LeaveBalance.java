package com.asiczen.api.attendancemgmt.payload.response;

import java.util.HashMap;

public class LeaveBalance {

	private String empId;
	private String orgId;
	private HashMap<String, Double> mapOrg;
	
	public LeaveBalance() {
		super();
	}

	public LeaveBalance(String empId, String orgId, HashMap<String, Double> mapOrg) {
		super();
		this.empId = empId;
		this.orgId = orgId;
		this.mapOrg = mapOrg;
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
	public HashMap<String, Double> getMapOrg() {
		return mapOrg;
	}
	public void setMapOrg(HashMap<String, Double> mapOrg) {
		this.mapOrg = mapOrg;
	}

	@Override
	public String toString() {
		return "LeaveBalance [empId=" + empId + ", orgId=" + orgId + ", mapOrg=" + mapOrg + "]";
	}
	
}
