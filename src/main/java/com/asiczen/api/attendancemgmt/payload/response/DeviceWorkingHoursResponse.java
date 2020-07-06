package com.asiczen.api.attendancemgmt.payload.response;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DeviceWorkingHoursResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long recordId;

	private String empId;

	private String orgId;

	private Date date;

	private Double calculatedhours;

	private String month;

	private String year;

	public DeviceWorkingHoursResponse() {
		super();
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getCalculatedhours() {
		return calculatedhours;
	}

	public void setCalculatedhours(Double calculatedhours) {
		this.calculatedhours = calculatedhours;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "DeviceWorkingHoursResponse [recordId=" + recordId + ", empId=" + empId + ", orgId=" + orgId + ", date="
				+ date + ", calculatedhours=" + calculatedhours + ", month=" + month + ", year=" + year + "]";
	}

}
