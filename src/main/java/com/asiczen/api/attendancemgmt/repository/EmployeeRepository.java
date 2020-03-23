package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{

	Optional<Employee> findByempEmailId(String emailId);
	
	Boolean existsByempEmailId(String emailId);
	
	Employee findByphoneNo(String phoneNo);
	
	Boolean existsByphoneNo(String phoneNo);
	
	Optional<List<Employee>> findByOrgIdAndEmpStatus(String orgId,String status);
	
	Optional<Long> countByOrgIdAndEmpStatus(String orgId,boolean status);
	
	Optional<Employee> findByEmpEmailIdAndEmpStatusAndOrgId(String emailId,boolean status,String orgId);
	
	Optional<Employee> findByEmpIdAndEmpStatusAndOrgId(String empId,boolean empStatus,String orgId);
}
