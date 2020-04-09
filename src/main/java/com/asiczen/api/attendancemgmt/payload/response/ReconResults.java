package com.asiczen.api.attendancemgmt.payload.response;

import java.util.List;

public class ReconResults {

	private String orgId;
	private String empId;
	private List<CalculatedResult> results;

	public ReconResults() {
		super();
	}

	public ReconResults(String orgId, String empId, List<CalculatedResult> results) {
		super();
		this.orgId = orgId;
		this.empId = empId;
		this.results = results;
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

	public List<CalculatedResult> getResults() {
		return results;
	}

	public void setResults(List<CalculatedResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ReconResults [orgId=" + orgId + ", empId=" + empId + ", results=" + results + "]";
	}

}
