package com.asiczen.api.attendancemgmt.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "empinout")
public class Empinout extends AuditModel{
	
	private static final long serialVersionUID = -5758884115578900226L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NotEmpty(message = "Organization Id is required/Can't be blank")
	@Size(min = 5, max = 10 , message="orgId should be between 5 to 10 characters")
	String orgId;
	
	@NotEmpty(message = "Emp Id is required/Can't be blank")
	@Size(min = 5, max = 10 , message="empId should be between 5 to 10 characters")
	String empId;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date utilDate;
     
    @Temporal(TemporalType.DATE)
    private Calendar utilTime;

    @NotEmpty(message = "type is required/Can't be blank")
	@Size(min = 2, max = 3 , message="type should be between 2 to 3 characters")
	String type;

}
