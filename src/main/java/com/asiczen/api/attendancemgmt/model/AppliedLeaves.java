package com.asiczen.api.attendancemgmt.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "appliedleaves")
public class AppliedLeaves extends AuditModel {

	private static final long serialVersionUID = -9184422320286031347L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Column(name = "orgid")
	private String orgId;

	@NotEmpty(message = "empId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	@Column(name = "empid", unique = true, nullable = false)
	private String empId;

	@Size(min = 5, max = 100, message = "Leave Type should be between 5 to 100 characters")
	@NotEmpty(message = "Leave Type is required/Can't be blank")
	@Column(unique = false)
	private String leaveTypeName;

	@NotNull(message = "From Date can't be Null")
	// @Future
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	// @Temporal(TemporalType.DATE)
	private LocalDate fromDate;

	@NotNull(message = "To Date can't be Null")
	// @Future
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	// @Temporal(TemporalType.DATE)
	private LocalDate toDate;

	private double quantity;

	@Size(min = 5, max = 100, message = "Please input some comments")
	private String comments;

	@Size(min = 5, max = 10, message = "status is mandatory")
	private String status;

	public AppliedLeaves() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AppliedLeaves [id=" + id + ", orgId=" + orgId + ", empId=" + empId + ", leaveTypeName=" + leaveTypeName
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", quantity=" + quantity + ", comments=" + comments
				+ ", status=" + status + "]";
	}

}
