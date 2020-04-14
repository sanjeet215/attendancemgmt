package com.asiczen.api.attendancemgmt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Department;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.Organization;
import com.asiczen.api.attendancemgmt.payload.request.EmployeeRequest;
import com.asiczen.api.attendancemgmt.payload.response.EmployeeByOrgResponse;
import com.asiczen.api.attendancemgmt.repository.DepartmentRepository;
import com.asiczen.api.attendancemgmt.repository.EmployeeRepository;
import com.asiczen.api.attendancemgmt.repository.OrganizationRepository;

@Service
public class EmpServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	OrganizationRepository orgRepo;

	@Autowired
	DepartmentRepository deptRepo;

	/* Get All Employees */
	public List<Employee> getAllEmployees() {

		if (empRepo.findAll().isEmpty()) {
			throw new ResourceNotFoundException("There are no employees Present in DB");
		} else {
			return empRepo.findAll();
		}
	}

	/* Get Employee by Organization */
	public List<Employee> getEmployeeByOrganization(String orgId) {

		Optional<List<Employee>> empList = empRepo.findByorgId(orgId);

		if (!empList.isPresent()) {
			throw new ResourceNotFoundException("There are no employees Present for Organization");
		} else {
			return empList.get();
		}
	}

	/* Add new Employee */

	public Employee addNewEmployee(Employee emp) {

		logger.info("Orgnization id is " + emp.getOrgId());
		// if (!orgRepo.existsByorganizationDisplayName(emp.getOrgId())) {
		// throw new ResourceNotFoundException("Organization doesn't exist " +
		// emp.getOrgId());
		// }
		Optional<Organization> org = orgRepo.findByorganizationDisplayName(emp.getOrgId());

		if (!org.isPresent()) {
			throw new ResourceNotFoundException("Organization doesn't exist " + emp.getOrgId());
		}

		Optional<Employee> findEmpByNumber = empRepo.findByphoneNo(emp.getPhoneNo());

		if (findEmpByNumber.isPresent()) {
			throw new ResourceAlreadyExistException("Phone no is already in Use!");
		}

		Optional<Employee> findEmpbyEmpid = empRepo.findByempId("Emp id is already in use");

		if (findEmpbyEmpid.isPresent()) {
			throw new ResourceAlreadyExistException("Employee Id is already in Use!");
		}

		return empRepo.save(emp);
	}

	/* Add Employee Final Method , 1st method will be removed after testing */
	public Employee postEmployee(EmployeeRequest emp) {

		if (orgRepo.existsByorganizationDisplayName(emp.getOrgId())) {
			throw new ResourceAlreadyExistException("Organization doesn't exist");
		}

		Optional<Employee> findEmpByNumber = empRepo.findByphoneNo(emp.getPhoneNo());

		if (findEmpByNumber.isPresent()) {
			throw new ResourceAlreadyExistException("Phone no is already in Use!");
		}

		Optional<Employee> findEmpbyEmpid = empRepo.findByempId("Emp id is already in use");

		if (findEmpbyEmpid.isPresent()) {
			throw new ResourceAlreadyExistException("Employee Id is already in Use!");
		}

		/* Get Department from Department name */

		Optional<List<Department>> department = deptRepo.findBydeptNameAndOrgId(emp.getDept(), emp.getOrgId());

		if (!department.isPresent()) {
			throw new ResourceNotFoundException("Department doesn't exist with Deptname: " + emp.getDept());
		} else {

			Employee employee = new Employee();
			populateEmployeeObject(emp, employee, department.get().get(0));
			return empRepo.save(employee);
		}

	}

	private void populateEmployeeObject(EmployeeRequest empRequest, Employee employee, Department department) {
		employee.setEmpId(empRequest.getEmpId());
		employee.setDept(department);
		employee.setOrgId(empRequest.getOrgId());
		employee.setEmpEmailId(empRequest.getEmpEmailId());
		employee.setEmpFirstName(empRequest.getEmpFirstName());
		employee.setEmpStatus(true);
		employee.setPhoneNo(empRequest.getPhoneNo());
	}

	/* Update Employee */
	// public Employee updateEmployee(@Valid Employee emp) {
	//
	// Optional<Employee> employee = empRepo.findById(emp.getId());
	// if (!employee.isPresent())
	// throw new ResourceNotFoundException("Emp with Id: " + emp.getId() + " not
	// found");
	//
	// return empRepo.findById(emp.getId()).map(nemp -> {
	// nemp.setEmpId(emp.getEmpId());
	// nemp.setEmpFirstName(emp.getEmpFirstName());
	// nemp.setEmpLsatName(emp.getEmpLsatName());
	// nemp.setEmpEmailId(emp.getEmpEmailId());
	// nemp.setNationalId(emp.getNationalId());
	// nemp.setEmpGender(emp.getEmpGender());
	// nemp.setDob(emp.getDob());
	// nemp.setDoj(emp.getDoj());
	// nemp.setMaritalStatus(emp.getMaritalStatus());
	// nemp.setFatherName(emp.getFatherName());
	//
	// nemp.setPhoneNo(emp.getPhoneNo());
	// nemp.setAddress(emp.getAddress());
	// nemp.setCity(emp.getCity());
	// nemp.setCountry(emp.getCountry());
	// nemp.setPostalCode(emp.getPostalCode());
	// nemp.setDesignation(emp.getDesignation());
	// nemp.setWorkingLocation(emp.getWorkingLocation());
	// nemp.setEmpType(emp.getEmpType());
	// nemp.setEmpStatus(emp.isEmpStatus());
	// nemp.setOrgId(emp.getOrgId());
	// return empRepo.save(nemp);
	//
	// }).orElseThrow(() -> new ResourceNotFoundException("Employee with Emp id : "
	// + emp.getEmpId() + "not found"));
	//
	// }

	public Employee updateEmployee(@Valid EmployeeRequest empRequest) {

		if (!orgRepo.existsByorganizationDisplayName(empRequest.getOrgId())) {
			throw new ResourceNotFoundException("Organization doesn't exist");
		}

		Optional<Employee> findEmpByNumber = empRepo.findByphoneNo(empRequest.getPhoneNo());

		if (findEmpByNumber.isPresent() && !empRequest.getEmpId().equals(empRequest.getEmpId())) {
			throw new ResourceAlreadyExistException("Phone no is already in Use!");
		}

		/* Get Department from Department name */

		Optional<List<Department>> department = deptRepo.findBydeptNameAndOrgId(empRequest.getDept(),
				empRequest.getOrgId());

		if (!department.isPresent()) {
			throw new ResourceNotFoundException("Department doesn't exist with Deptname: " + empRequest.getDept());
		}

		Optional<Employee> employee = empRepo.findByempId(empRequest.getEmpId());
		if (!employee.isPresent())
			throw new ResourceNotFoundException("Emp with Id: " + empRequest.getEmpId() + " not found");

		return empRepo.findByempId(empRequest.getEmpId()).map(nemp -> {
			nemp.setEmpId(empRequest.getEmpId());
			nemp.setEmpFirstName(empRequest.getEmpFirstName());
			nemp.setEmpLsatName(empRequest.getEmpLsatName());
			nemp.setNationalId(empRequest.getNationalId());
			nemp.setEmpGender(empRequest.getEmpGender());
			nemp.setDob(empRequest.getDob());
			nemp.setDoj(empRequest.getDoj());
			nemp.setMaritalStatus(empRequest.getMaritalStatus());
			nemp.setFatherName(empRequest.getFatherName());
			nemp.setPhoneNo(empRequest.getPhoneNo());
			nemp.setAddress(empRequest.getAddress());
			nemp.setCity(empRequest.getCity());
			nemp.setCountry(empRequest.getCountry());
			nemp.setPostalCode(empRequest.getPostalCode());
			nemp.setDesignation(empRequest.getDesignation());
			nemp.setWorkingLocation(empRequest.getWorkingLocation());
			nemp.setEmpType(empRequest.getEmpType());
			nemp.setEmpStatus(empRequest.isEmpStatus());
			nemp.setOrgId(empRequest.getOrgId());
			nemp.setDept(department.get().get(0));
			return empRepo.save(nemp);

		}).orElseThrow(
				() -> new ResourceNotFoundException("Employee with Emp id : " + empRequest.getEmpId() + "not found"));

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

	/* Count of Employees By Organization Id */
	public Long countEmployeebyOrganization(String orgId, boolean status) {
		Optional<Long> count = empRepo.countByOrgIdAndEmpStatus(orgId, status);
		if (!count.isPresent()) {
			return 0L;
		} else {
			return count.get();
		}
	}

	/* Count of All Employees for Super Admin */

	public Long countEmployee() {
		return empRepo.count();

	}

	/* Get Employee by email Id for MY Profile */

	public Employee findByEmailid(String emailId) {

		Optional<Employee> emp = empRepo.findByempEmailId(emailId);

		if (!emp.isPresent()) {
			throw new ResourceNotFoundException("Employee with email Id :" + emailId + "not found");
		} else {
			return emp.get();
		}

	}

	/* Validate Employee , Mobile end point */

	public boolean validateEmployee(String empId, String orgId) {

		Optional<Employee> emp = empRepo.findByEmpIdAndEmpStatusAndOrgId(empId, true, orgId);

		if (!emp.isPresent()) {
			throw new ResourceNotFoundException(
					"Employee with empId: " + empId + " and OrgId: " + orgId + " not found in database");
		} else {
			return true;
		}

	}

	public EmployeeByOrgResponse getEmpListbyOrg(String orgId) {

		Optional<List<Employee>> emp = empRepo.findByorgId(orgId);

		if (!emp.isPresent()) {
			throw new ResourceNotFoundException("No Employees registerd for organization");
		}

		EmployeeByOrgResponse response = new EmployeeByOrgResponse();
		List<String> empIds = new ArrayList<>();

		emp.get().forEach(item -> {
			empIds.add(item.getEmpId());
		});

		response.setOrgId(orgId);
		response.setEmpId(empIds);

		return response;
	}

}
