package com.asiczen.api.attendancemgmt.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


//@Entity
//@Table(name = "Organizationbkp")
public class Organizationbkp extends AuditModel{

	private static final long serialVersionUID = 8345531028140196989L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Size(min = 5, max = 10 , message="orgId should be between 5 to 10 characters")
	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Column(name = "org_id",unique=true)
	private String orgId;

	
	@Size(min = 5, max = 15 ,message="orgName should be between 5 to 10 characters")
	@NotEmpty(message = "Organization Name is required/Can't be blank")
	private String orgName;

	@Size(min = 5, max = 50)
	private String orgDescription;

	@NotNull(message = "status can't be null")
	private boolean status;

	public Organizationbkp() {
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDescription() {
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}

