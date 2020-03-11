package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "totalleaves")
public class TotalLeaves extends AuditModel{

	private static final long serialVersionUID = 8863734302556105159L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Size(min = 5, max = 10 , message="orgId should be between 5 to 10 characters")
	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Column(name = "org_id",unique=true)
	private String orgId;
	
	@Min(value = 0, message = "The value must be positive")
	private int totalLeaves;
	
	@NotNull(message = "status can't be null, default is false")
	private boolean status;

	public TotalLeaves() {
		super();
	}

	public TotalLeaves(long id,
			@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters") @NotEmpty(message = "Organization Id is required/Can't be blank") String orgId,
			@Min(value = 0, message = "The value must be positive") int totalLeaves,
			@NotNull(message = "status can't be null, default is false") boolean status) {
		super();
		this.id = id;
		this.orgId = orgId;
		this.totalLeaves = totalLeaves;
		this.status = status;
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

	public int getTotalLeaves() {
		return totalLeaves;
	}

	public void setTotalLeaves(int totalLeaves) {
		this.totalLeaves = totalLeaves;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
	
}
