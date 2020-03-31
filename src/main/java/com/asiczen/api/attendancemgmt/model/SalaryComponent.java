package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SalaryComponent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "componentName is required/Can't be blank")
	@Size(min = 5, max = 10, message = "componentName should be between 5 to 30 characters")
	@Column(name = "componentName", nullable = false)
	private String componentName;
	
	@DecimalMin(value = "999.1", inclusive = false)
	@DecimalMax(value = "1000000", inclusive = false)
	private Double amount;
}
