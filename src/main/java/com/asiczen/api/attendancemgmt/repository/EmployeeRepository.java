package com.asiczen.api.attendancemgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{

	Employee findByempEmailId(String emailId);
	
	Boolean existsByempEmailId(String emailId);
	
	Employee findByphoneNo(String phoneNo);
	
	Boolean existsByphoneNo(String phoneNo);
}
