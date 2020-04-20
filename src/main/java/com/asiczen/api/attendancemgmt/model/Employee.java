package com.asiczen.api.attendancemgmt.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee extends AuditModel {

	private static final long serialVersionUID = 3077034967531048897L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NotEmpty(message = "empId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	@Column(name = "empid", unique = true, nullable = false)
	private String empId;

	@NotEmpty(message = "empfname is required/Can't be blank")
	@Size(min = 2, max = 10, message = "empfname should be between 5 to 10 characters")
	@Column(name = "empfname", unique = false, nullable = false)
	private String empFirstName;

	@NotEmpty(message = "emplname is required/Can't be blank")
	@Size(min = 1, max = 10, message = "emplname should be between 5 to 10 characters")
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
	@Column(name = "empPhoneNo", unique = true, nullable = false)
	private String phoneNo;

	private String address;

	private String city;

	private String country;

	private int postalCode;

	private String designation;

	private String workingLocation;

	private String empType;

	private boolean empStatus;

	@Column(name = "orgid", nullable = false)
	private String orgId;

	@ManyToOne
	@JoinColumn(name = "DEPT_ID")
	private Department dept;

	public Employee() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

}
