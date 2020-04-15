package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.EmployeeDetails;
import com.asiczen.api.attendancemgmt.repository.EmployeeDetailsRepository;
import com.asiczen.api.attendancemgmt.repository.EmployeeRepository;

@Service
public class EmployeeDetailsServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailsServiceImpl.class);

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	EmployeeDetailsRepository empDetailsRepo;

	/* Post Employee Details */
	public EmployeeDetails postEmployeeDetails(EmployeeDetails employeeDetails) {

		Optional<Employee> employee = empRepo.findByOrgIdAndEmpId(employeeDetails.getOrgId(),
				employeeDetails.getEmpId());

		if (!employee.isPresent()) {
			throw new ResourceNotFoundException("Employee not Found with empId and OrgID ");
		}

		Optional<EmployeeDetails> empDetails = empDetailsRepo.findByOrgIdAndEmpId(employeeDetails.getOrgId(),
				employeeDetails.getEmpId());

		if (empDetails.isPresent()) {
			throw new ResourceAlreadyExistException("REcord with Empid and OrgID already present.Please try to update");
		}

		return empDetailsRepo.save(employeeDetails);

	}

	/* Get Employee Details - employee specific */

	public EmployeeDetails getEmployeeDetails(String orgid, String empid) {
		Optional<EmployeeDetails> empDetails = empDetailsRepo.findByOrgIdAndEmpId(orgid, empid);

		if (!empDetails.isPresent()) {
			throw new ResourceNotFoundException("Employee not Found with empId and OrgID ");
		}

		return empDetails.get();
	}

	/* Get all Employee Details - Organization specific */

	public List<EmployeeDetails> getAllEmployeeDetails(String orgid) {

		Optional<List<EmployeeDetails>> empDetailList = empDetailsRepo.findByorgId(orgid);

		if (!empDetailList.isPresent()) {
			throw new ResourceNotFoundException("No employees details are added yet");
		}

		return empDetailList.get();
	}

	/* Update Employee Details */

}
