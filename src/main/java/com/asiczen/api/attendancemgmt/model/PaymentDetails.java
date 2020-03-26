package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PaymentDetails extends AuditModel{

	private static final long serialVersionUID = -1542564333242526944L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(min = 5, max = 10, message = "orgid should be between 5 to 10 characters")
	@NotEmpty(message = "orgid is required/Can't be blank")
	@Column(name = "orgid")
	private String orgId;
	
	@NotEmpty(message = "empId is required/Can't be blank")
	@Size(min = 5, max = 10, message = "empId should be between 5 to 10 characters")
	@Column(name = "empid", nullable = false)
	private String empId;
	
	@NotEmpty(message = "componentName is required/Can't be blank")
	@Size(min = 5, max = 10, message = "componentName should be between 5 to 30 characters")
	@Column(name = "componentName", nullable = false)
	private String componentName;
	
	@DecimalMin(value = "999.1", inclusive = false)
	@DecimalMax(value = "1000000", inclusive = false)
	private Double quantity;

	public PaymentDetails() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "PaymentDetails [id=" + id + ", orgId=" + orgId + ", empId=" + empId + ", componentName=" + componentName
				+ ", quantity=" + quantity + "]";
	}
	
	
}
