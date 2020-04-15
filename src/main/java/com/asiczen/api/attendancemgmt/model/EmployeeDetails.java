package com.asiczen.api.attendancemgmt.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "employeedetails")
public class EmployeeDetails extends AuditModel {

	private static final long serialVersionUID = -2710574766127795014L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long employeedetailid;

	private String uanNo;
	private String pfNo;
	private String esiNo;
	private String panNo;
	private String epsNo;
	private String grade;

	@Column(name = "orgid", nullable = false)
	private String orgId;

	@Column(name = "empid", nullable = false)
	private String empId;

	public EmployeeDetails() {
		super();
	}

	public Long getEmployeedetailid() {
		return employeedetailid;
	}

	public void setEmployeedetailid(Long employeedetailid) {
		this.employeedetailid = employeedetailid;
	}

	public String getUanNo() {
		return uanNo;
	}

	public void setUanNo(String uanNo) {
		this.uanNo = uanNo;
	}

	public String getPfNo() {
		return pfNo;
	}

	public void setPfNo(String pfNo) {
		this.pfNo = pfNo;
	}

	public String getEsiNo() {
		return esiNo;
	}

	public void setEsiNo(String esiNo) {
		this.esiNo = esiNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getEpsNo() {
		return epsNo;
	}

	public void setEpsNo(String epsNo) {
		this.epsNo = epsNo;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

}
