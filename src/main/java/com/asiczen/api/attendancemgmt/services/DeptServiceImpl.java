package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Department;
import com.asiczen.api.attendancemgmt.repository.DepartmentRepository;

@Service
public class DeptServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(DeptServiceImpl.class);
	
	@Autowired
	DepartmentRepository deptRepo;
	
	/* Add Organization */

	public Department addDepartment(Department dept) {

		Optional<Department> department = deptRepo.findBydeptId(dept.getDeptId());

		if (department.isPresent()) {
			logger.error("Organization Id" + dept.getDeptId() + "already exist in Database");
			throw new ResourceAlreadyExistException(dept.getDeptName());
		}

		return deptRepo.save(dept);
	}

	public List<Department> getAllDepartments() {

		if (deptRepo.findAll().isEmpty()) {
			throw new ResourceNotFoundException("There are no department registered for org id.");
		} else {
			return deptRepo.findAll();
		}
	}
	
	public Department updateDepartment(Department newDept) {
		Optional<Department> department = deptRepo.findById(newDept.getDeptId());
		if (!department.isPresent())
			throw new ResourceNotFoundException("Department " + newDept.getDeptId() + " not found");

		return deptRepo.findById(newDept.getDeptId()).map(ndepartment -> {
			
			ndepartment.setDeptName(newDept.getDeptName());
			ndepartment.setDescription(newDept.getDescription());
			ndepartment.setOrgId(newDept.getOrgId());
			ndepartment.setStatus(newDept.isStatus());

			return deptRepo.save(ndepartment);

		}).orElseThrow(() -> new ResourceNotFoundException("Department ID "+ newDept.getDeptId() + "not found"));

	}

}
