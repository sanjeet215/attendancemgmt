package com.asiczen.api.attendancemgmt.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Department", uniqueConstraints = @UniqueConstraint(columnNames = { "deptName", "orgId" }))
public class Department extends AuditModel {

	private static final long serialVersionUID = 9025174144081979470L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long deptId;

	@NotEmpty(message = "Department Name is required/Can't be blank")
	@Size(min = 2, max = 32, message = "deptName should be between 2 to 32 characters")
	@Column(name = "deptName")
	private String deptName;

	@NotEmpty(message = "Department Description is required/Can't be blank")
	@Size(min = 5, max = 100, message = "Department Description should be between 5 to 100 characters")
	@Column(name = "description")
	private String description;

	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Size(min = 5, max = 10, message = "orgId should be between 5 to 10 characters")
	private String orgId;

	@NotNull
	private boolean status;

	@JsonIgnore
	@OneToMany(mappedBy = "dept")
	private List<Employee> emps;

	public Department() {
		super();
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Employee> getEmps() {
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}

	@Override
	public String toString() {
		return "Department [deptId=" + deptId + ", deptName=" + deptName + ", description=" + description + ", orgId="
				+ orgId + ", status=" + status + ", emps=" + emps + "]";
	}

}
