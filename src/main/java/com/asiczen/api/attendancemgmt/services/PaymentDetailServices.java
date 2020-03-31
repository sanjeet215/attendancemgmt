package com.asiczen.api.attendancemgmt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.payload.request.Component;
import com.asiczen.api.attendancemgmt.payload.request.SalaryComponent;
import com.asiczen.api.attendancemgmt.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailServices<PaymentDetails> {

	@Autowired
	PaymentDetailsRepository payRepo;

	private static final Logger loger = LoggerFactory.getLogger(PaymentDetailServices.class);

	public SalaryComponent postEmployeeSalary(SalaryComponent component) {

		List<com.asiczen.api.attendancemgmt.model.PaymentDetails> records = new ArrayList<com.asiczen.api.attendancemgmt.model.PaymentDetails>();

		component.getComponents().forEach(item -> {
			Optional<com.asiczen.api.attendancemgmt.model.PaymentDetails> salComponent = payRepo
					.findByOrgIdAndEmpIdAndComponentName(component.getOrgId(), component.getEmpId(),
							item.getComponent());

			if (!salComponent.isPresent()) {
				throw new ResourceAlreadyExistException("Salary component already present." + item.getComponent());
			} else {
				com.asiczen.api.attendancemgmt.model.PaymentDetails record = new com.asiczen.api.attendancemgmt.model.PaymentDetails();
				records.add(record);
			}

		});

		List<com.asiczen.api.attendancemgmt.model.PaymentDetails> newRecords = payRepo.saveAll(records);

		SalaryComponent salary = new SalaryComponent();

		salary.setOrgId(component.getOrgId());
		salary.setEmpId(component.getEmpId());

		Component savedcomponent = new Component();
		List<Component> savedcomponents = new ArrayList<Component>();

		newRecords.forEach(item -> {
			savedcomponent.setComponent(item.getComponentName());
			savedcomponent.setAmount(item.getQuantity());

			savedcomponents.add(savedcomponent);
		});

		salary.setComponents(savedcomponents);

		return salary;
	}

	
	public SalaryComponent getEmployeeSalary(String orgId,String empId) {
		
		Optional<List<com.asiczen.api.attendancemgmt.model.PaymentDetails>> salaryComponents = payRepo.findByOrgIdAndEmpId(orgId, empId);
		
		if(!salaryComponents.isPresent()) {
			throw new ResourceNotFoundException("Salary for the employee is not registered yet. "+ empId);
		}
		
		
		List<Component> components = new ArrayList<Component>();
		
		salaryComponents.get().forEach(item->{
			Component component = new Component();
			component.setComponent(item.getComponentName());
			component.setAmount(item.getQuantity());
			
			components.add(component);
		});
		
		
		return new SalaryComponent(orgId,empId,components);
		
	}
}
