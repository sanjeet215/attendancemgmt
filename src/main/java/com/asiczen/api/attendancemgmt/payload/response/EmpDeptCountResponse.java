package com.asiczen.api.attendancemgmt.payload.response;

public class EmpDeptCountResponse {

	private String countType;
	private Long count;

	public EmpDeptCountResponse() {
		super();
	}

	public EmpDeptCountResponse(String countType, Long count) {
		super();
		this.countType = countType;
		this.count = count;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "EmpDeptCountResponse [countType=" + countType + ", count=" + count + "]";
	}

}
