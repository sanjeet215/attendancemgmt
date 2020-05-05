package com.asiczen.api.attendancemgmt.payload.request;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Component {

	@NotEmpty(message = "componentName is required/Can't be blank")
	@Size(min = 5, max = 100, message = "componentName should be between 5 to 30 characters")
	private String component;
	
	@DecimalMin(value = "0.0", inclusive = false)
	@DecimalMax(value = "100000000", inclusive = false)
	private Double amount;

	public Component() {
		super();
	}

	public Component(String component, Double amount) {
		super();
		this.component = component;
		this.amount = amount;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
}
