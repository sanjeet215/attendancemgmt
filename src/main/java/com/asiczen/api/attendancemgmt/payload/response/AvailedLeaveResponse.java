package com.asiczen.api.attendancemgmt.payload.response;

public class AvailedLeaveResponse {

	private String leaveTypeName;
	private String empid;
	private String orgid;
	private double availedLeaves;
	private double availableLeaves;
	private double balance;
	private String status;

	public AvailedLeaveResponse() {
		super();
	}
	
	public AvailedLeaveResponse(String leaveTypeName, String empid, String orgid, double availedLeaves,
			double availableLeaves, double balance, String status) {
		super();
		this.leaveTypeName = leaveTypeName;
		this.empid = empid;
		this.orgid = orgid;
		this.availedLeaves = availedLeaves;
		this.availableLeaves = availableLeaves;
		this.balance = balance;
		this.status = status;
	}



	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public double getAvailedLeaves() {
		return availedLeaves;
	}

	public void setAvailedLeaves(double availedLeaves) {
		this.availedLeaves = availedLeaves;
	}

	public double getAvailableLeaves() {
		return availableLeaves;
	}

	public void setAvailableLeaves(double availableLeaves) {
		this.availableLeaves = availableLeaves;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AvailedLeaveResponse [leaveTypeName=" + leaveTypeName + ", empid=" + empid + ", orgid=" + orgid
				+ ", availedLeaves=" + availedLeaves + ", availableLeaves=" + availableLeaves + ", balance=" + balance
				+ ", status=" + status + "]";
	}

	
}
