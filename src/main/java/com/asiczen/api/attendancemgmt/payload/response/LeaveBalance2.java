package com.asiczen.api.attendancemgmt.payload.response;

public class LeaveBalance2 {

	private String leaveName;
	private double balance;

	public LeaveBalance2() {
		super();
	}

	public LeaveBalance2(String leaveName, double balance) {
		super();
		this.leaveName = leaveName;
		this.balance = balance;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "LeaveBalance [leaveName=" + leaveName + ", balance=" + balance + "]";
	}

}
