package com.asiczen.api.attendancemgmt.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.exception.SomeInternalException;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.PayStructure;
import com.asiczen.api.attendancemgmt.payload.request.Component;
import com.asiczen.api.attendancemgmt.payload.request.SalaryComponent;
import com.asiczen.api.attendancemgmt.repository.EmployeeRepository;
import com.asiczen.api.attendancemgmt.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailServices {

	@Autowired
	PaymentDetailsRepository payRepo;

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	PayStructureServiceImpl payStructure;

	private static final Logger loger = LoggerFactory.getLogger(PaymentDetailServices.class);

	public SalaryComponent postEmployeeSalary(SalaryComponent component) {

		/* Validate Employee id and Company Id */

		Optional<Employee> emp = empRepo.findByEmpIdAndEmpStatusAndOrgId(component.getEmpId(), true,
				component.getOrgId());

		if (!emp.isPresent()) {
			throw new ResourceNotFoundException("Employee with empid " + component.getEmpId()
					+ " doesn't exist for organization: " + component.getOrgId());
		}

		List<com.asiczen.api.attendancemgmt.model.PaymentDetails> records = new ArrayList<com.asiczen.api.attendancemgmt.model.PaymentDetails>();

		component.getComponents().forEach(item -> {
			Optional<com.asiczen.api.attendancemgmt.model.PaymentDetails> salComponent = payRepo
					.findByOrgIdAndEmpIdAndComponentName(component.getOrgId(), component.getEmpId(),
							item.getComponent());

			if (salComponent.isPresent()) {
				throw new ResourceAlreadyExistException("Salary component already present." + item.getComponent());
			} else {
				com.asiczen.api.attendancemgmt.model.PaymentDetails record = new com.asiczen.api.attendancemgmt.model.PaymentDetails();
				record.setOrgId(component.getOrgId());
				record.setEmpId(component.getEmpId());
				record.setComponentName(item.getComponent());
				record.setQuantity(item.getAmount());

				records.add(record);
			}

		});

		// records.forEach(item->{
		// System.out.println(item.getEmpId());
		// });

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

	public SalaryComponent getEmployeeSalary(String orgId, String empId) {

		Optional<Employee> emp = empRepo.findByEmpIdAndEmpStatusAndOrgId(empId, true, orgId);

		if (!emp.isPresent()) {
			throw new ResourceNotFoundException(
					"Employee with empid " + empId + " doesn't exist for organization: " + orgId);
		}

		/* Get all registered components from paystructure */

		List<PayStructure> registeredComponents = payStructure.getSalaryComponentsByOrganization(orgId);

		Optional<List<com.asiczen.api.attendancemgmt.model.PaymentDetails>> salaryComponents = payRepo
				.findByOrgIdAndEmpId(orgId, empId);

		if (!salaryComponents.isPresent()) {
			List<Component> response = new ArrayList<Component>();

			registeredComponents.forEach(item -> {
				Component component = new Component();
				component.setComponent(item.getComponentName());
				component.setAmount(0.0);

				response.add(component);
			});

			return new SalaryComponent(orgId, empId, response);
		} else {

			List<Component> response = new ArrayList<Component>();

			Map<String, Double> components = new HashMap<String, Double>();

			registeredComponents.forEach(item -> {
				components.put(item.getComponentName(), 0.0);
			});

			salaryComponents.get().forEach(item -> {
				if (components.containsKey(item.getComponentName())) {
					components.put(item.getComponentName(), item.getQuantity());
				}
			});

			// covert components to array of objects

			components.forEach((k, v) -> {
				Component component = new Component();
				component.setComponent(k);
				component.setAmount(v);

				response.add(component);
			});

			return new SalaryComponent(orgId, empId, response);

		}

	}

	public SalaryComponent updateEmployeeSalary(SalaryComponent component) {

		/* Validate Employee id and Company Id */

		Optional<Employee> emp = empRepo.findByEmpIdAndEmpStatusAndOrgId(component.getEmpId(), true,
				component.getOrgId());

		if (!emp.isPresent()) {
			throw new ResourceNotFoundException("Employee with empid " + component.getEmpId()
					+ " doesn't exist for organization: " + component.getOrgId());
		}

		/* New Records will be inserted into DB, Existing records will be updated */

		List<com.asiczen.api.attendancemgmt.model.PaymentDetails> newrecords = new ArrayList<com.asiczen.api.attendancemgmt.model.PaymentDetails>();
		List<com.asiczen.api.attendancemgmt.model.PaymentDetails> existingrecords = new ArrayList<com.asiczen.api.attendancemgmt.model.PaymentDetails>();

		component.getComponents().forEach(item -> {
			Optional<com.asiczen.api.attendancemgmt.model.PaymentDetails> salComponent = payRepo
					.findByOrgIdAndEmpIdAndComponentName(component.getOrgId(), component.getEmpId(),
							item.getComponent());

			if(item.getAmount()<0 && item.getAmount()>100000000) {
				throw new SomeInternalException("Amount can't be negative. or greater than 100000000");
			}
			
			if (!salComponent.isPresent()) {
				com.asiczen.api.attendancemgmt.model.PaymentDetails record = new com.asiczen.api.attendancemgmt.model.PaymentDetails();
				record.setOrgId(component.getOrgId());
				record.setEmpId(component.getEmpId());
				record.setComponentName(item.getComponent());
				record.setQuantity(item.getAmount());
				newrecords.add(record);

			} else {
				com.asiczen.api.attendancemgmt.model.PaymentDetails record = new com.asiczen.api.attendancemgmt.model.PaymentDetails();
				record.setOrgId(component.getOrgId());
				record.setEmpId(component.getEmpId());
				record.setComponentName(item.getComponent());
				record.setQuantity(item.getAmount());

				existingrecords.add(record);
			}

		});
		
		/* Update Existing Records */

		List<Component> savedcomponents = new ArrayList<>();

		existingrecords.forEach(item -> {
			Optional<com.asiczen.api.attendancemgmt.model.PaymentDetails> dbEntry = payRepo
					.findByOrgIdAndEmpIdAndComponentName(item.getOrgId(), item.getEmpId(), item.getComponentName());

			if (!dbEntry.isPresent()) {
				throw new ResourceNotFoundException("Component can't be update here");
			} else {
				dbEntry.get().setQuantity(item.getQuantity());
				payRepo.save(dbEntry.get());

				savedcomponents.add(new Component(item.getComponentName(), item.getQuantity()));
			}
		});
		
		/* Save Existing records*/
		
		newrecords.forEach(item ->{
			payRepo.save(item);
			savedcomponents.add(new Component(item.getComponentName(), item.getQuantity()));
		});
		

		return new SalaryComponent(component.getOrgId(), component.getEmpId(), savedcomponents);
	}

}
