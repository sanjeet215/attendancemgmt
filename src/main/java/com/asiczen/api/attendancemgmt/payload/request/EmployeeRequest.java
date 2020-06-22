package com.asiczen.api.attendancemgmt.payload.request;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmployeeRequest {

	@NotEmpty(message = "empId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	@Column(name = "empid", unique = true, nullable = false)
	private String empId;

	@NotEmpty(message = "empfname is required/Can't be blank")
	@Size(min = 1, max = 16, message = "Emp First Name should be between 1 to 16 characters")
	@Column(name = "empfname", unique = false, nullable = false)
	private String empFirstName;

	@NotEmpty(message = "emplname is required/Can't be blank")
	@Size(min = 1, max = 16, message = "Emp Last Name should be between 1 to 16 characters")
	@Column(name = "emplname")
	private String empLsatName;

	@NotEmpty(message = "empEmailId is required/Can't be blank")
	@Size(min = 5, max = 50, message = "empEmailId should be between 5 to 50 characters")
	@Column(name = "empEmailId", unique = true, nullable = false, length = 50)
	@Pattern(regexp = "^(.+)@(.+)$", message = "Email Id is invalid")
	@Email
	private String empEmailId;

	@Column(name = "nationalId", nullable = true)
	private String nationalId;

	@Column(name = "empGender", nullable = true)
	private String empGender;

	private Date Dob;

	private Date Doj;

	private String maritalStatus;

	private String fatherName;

	@NotEmpty(message = "empPhoneNo is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empPhoneNo should be between 5 to 10 characters")
	@Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Mobile number is invalid")
	@Column(name = "phoneNo", unique = true, nullable = false)
	private String phoneNo;

	private String address;

	private String city;

	private String country;

	private int postalCode;

	private String designation;

	private String workingLocation;

	private String empType;

	private boolean empStatus;

	@NotEmpty(message = "orgId is required/Can't be blank")
	@Column(name = "orgid", nullable = false)
	private String orgId;

	private String dept;

	public EmployeeRequest() {
		super();
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpLsatName() {
		return empLsatName;
	}

	public void setEmpLsatName(String empLsatName) {
		this.empLsatName = empLsatName;
	}

	public String getEmpEmailId() {
		return empEmailId;
	}

	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getEmpGender() {
		return empGender;
	}

	public void setEmpGender(String empGender) {
		this.empGender = empGender;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public Date getDoj() {
		return Doj;
	}

	public void setDoj(Date doj) {
		Doj = doj;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getWorkingLocation() {
		return workingLocation;
	}

	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = workingLocation;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public boolean isEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(boolean empStatus) {
		this.empStatus = empStatus;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "EmployeeRequest [empId=" + empId + ", empFirstName=" + empFirstName + ", empLsatName=" + empLsatName
				+ ", empEmailId=" + empEmailId + ", nationalId=" + nationalId + ", empGender=" + empGender + ", Dob="
				+ Dob + ", Doj=" + Doj + ", maritalStatus=" + maritalStatus + ", fatherName=" + fatherName
				+ ", phoneNo=" + phoneNo + ", address=" + address + ", city=" + city + ", country=" + country
				+ ", postalCode=" + postalCode + ", designation=" + designation + ", workingLocation=" + workingLocation
				+ ", empType=" + empType + ", empStatus=" + empStatus + ", orgId=" + orgId + ", dept=" + dept + "]";
	}

}
