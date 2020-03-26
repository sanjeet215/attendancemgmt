package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PayStructure extends AuditModel {

	private static final long serialVersionUID = 2987881849300264783L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 5, max = 10, message = "orgid should be between 5 to 10 characters")
	@NotEmpty(message = "orgid is required/Can't be blank")
	@Column(name = "orgid")
	private String orgId;

	@NotEmpty(message = "componentName is required/Can't be blank")
	@Size(min = 5, max = 10, message = "componentName should be between 5 to 30 characters")
	@Column(name = "empid", nullable = false)
	private String componentName;

	@NotEmpty(message = "sign is required/Can't be blank")
	@Size(min = 5, max = 10, message = "sign should be between 5 to 10 characters")
	@Column(name = "sign", nullable = false)
	private String sign;

	public PayStructure() {
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

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "PayStructure [id=" + id + ", orgId=" + orgId + ", componentName=" + componentName + ", sign=" + sign
				+ "]";
	}

}
