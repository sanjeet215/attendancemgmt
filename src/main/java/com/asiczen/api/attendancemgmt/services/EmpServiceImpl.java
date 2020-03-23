package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.repository.EmployeeRepository;

@Service
public class EmpServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);
	
	@Autowired
	EmployeeRepository empRepo;

	/* Get All Employees */
	public List<Employee> getAllEmployees() {

		if (empRepo.findAll().isEmpty()) {
			throw new ResourceNotFoundException("There are no employees Present in DB");
		} else {
			return empRepo.findAll();
		}
	}

	/* Add new Employee */

	public Employee addNewEmployee(Employee emp) {
		logger.info("TestLine Testline" + emp.getOrgId());
		
		return empRepo.save(emp);
	}

	/* Update Employee */
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

		}).orElseThrow(() -> new ResourceNotFoundException("Employee with Emp id : " + emp.getEmpId() + "not found"));

	}

	/* delete Employee */
	public void deleteEmployee(Employee emp) {
		empRepo.delete(emp);
	}

	public Employee getEmployeeById(long id) {
		Optional<Employee> emp = empRepo.findById(id);

		if (!emp.isPresent())
			throw new ResourceNotFoundException("Employee not Found with id: " + id);

		return emp.get();
	}

	
	/*Count of Employees By Organization Id */
	public Long countEmployeebyOrganization(String orgId, boolean status) {
		Optional<Long> count = empRepo.countByOrgIdAndEmpStatus(orgId, status);
		if (!count.isPresent()) {
			return 0L;
		} else {
			return count.get();
		}
	}
	
	
	/* Get Employee by email Id for MY Profile*/
	
	public Employee findByEmailid(String emailId) {

		Optional<Employee> emp = empRepo.findByempEmailId(emailId);

		if (!emp.isPresent()) {
			throw new ResourceNotFoundException("Employee with email Id :" + emailId + "not found");
		} else {
			return emp.get();
		}

	}
	
	
	/* Validate Employee , Mobile end point*/
	
	public boolean validateEmployee(String empId,String orgId) {
		
		Optional<Employee> emp = empRepo.findByEmpIdAndEmpStatusAndOrgId(empId, true,orgId);
		
		if(!emp.isPresent()) {
			throw new ResourceNotFoundException("Employee with empId: "+empId+" and OrgId: "+orgId+" not found in database");
		} else {
			return true;
		}
		
	}
	
}
