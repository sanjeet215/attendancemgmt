package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.repository.EmployeeRepository;


@Service
public class EmpServiceImpl {

	@Autowired
	EmployeeRepository empRepo;
	
	
//	@Autowired
//	MailServiceImpl mailSender;
	
	/* Get All Employees */
	public List<Employee> getAllEmployees() {
		
		//testEmail();

		if (empRepo.findAll().isEmpty()) {
			throw new ResourceNotFoundException("There are no employees Present in DB");
		} else {
			return empRepo.findAll();
		}
	}
	
	/* Add new Employee */
	
	public Employee addNewEmployee(Employee emp) {
		return empRepo.save(emp);
	}
	
	/*Update Employee */
	public Employee updateEmployee(@Valid Employee emp) {
		
		Optional<Employee> employee = empRepo.findById(emp.getId());
		if (!employee.isPresent())
			throw new ResourceNotFoundException("Emp with Id: " + emp.getId() + " not found");

		return empRepo.findById(emp.getId()).map(nemp -> {
					nemp.setEmpId(emp.getEmpId());
					nemp.setEmpFirstName(emp.getEmpFirstName());
					nemp.setEmpLsatName(emp.getEmpLsatName());
					nemp.setEmpEmailId(emp.getEmpEmailId());
					nemp.setNationalId(emp.getNationalId());
					nemp.setEmpGender(emp.getEmpGender());
					nemp.setDob(emp.getDob());
					nemp.setDoj(emp.getDoj());
					nemp.setMaritalStatus(emp.getMaritalStatus());
					nemp.setFatherName(emp.getFatherName());
					
					nemp.setPhoneNo(emp.getPhoneNo());
					nemp.setAddress(emp.getAddress());
					nemp.setCity(emp.getCity());
					nemp.setCountry(emp.getCountry());
					nemp.setPostalCode(emp.getPostalCode());
					nemp.setDesignation(emp.getDesignation());
					nemp.setWorkingLocation(emp.getWorkingLocation());
					nemp.setEmpType(emp.getEmpType());
					nemp.setEmpStatus(emp.isEmpStatus());
					nemp.setOrgId(emp.getOrgId());
			return empRepo.save(nemp);

		}).orElseThrow(() -> new ResourceNotFoundException("Employee with Emp id : "+ emp.getEmpId() + "not found"));

	
		
	}
	
	/* delete Employee */
	public void deleteEmployee(Employee emp) {
		empRepo.delete(emp);
	}
	
	
	/* Test Email */
	
//	public void testEmail() {
//		
//			System.out.println("TestEmail is invoked");
//			Mail mail = new Mail();
//	        mail.setMailFrom("sanjeet.mail.test@gmail.com");
//	        mail.setMailTo("sanjeet215@gmail.com");
//	        mail.setMailSubject("Spring Boot - Email Example");
//	        mail.setMailContent("Learn How to send Email using Spring Boot!!!\n\nThanks\nwww.technicalkeeda.com");
//	        mailSender.sendEmail(mail);
//	        System.out.println("TestEmail is Finished");
//	}
//	
	
	
	
	// Add one Employee for Testing
	
	public void addEmp() {
		Employee emp = new Employee();
		
		emp.setEmpId("az12345");
		emp.setEmpFirstName("Test");
		emp.setEmpLsatName("test");
		emp.setEmpEmailId("test@gmail.com");
		emp.setNationalId("Tetss");
		emp.setEmpGender("test");
		emp.setOrgId("testId");
		empRepo.save(emp);
	}
}

