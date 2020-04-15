package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiczen.api.attendancemgmt.model.EmployeeDetails;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

	Optional<EmployeeDetails> findByOrgIdAndEmpId(String orgId, String empId);

	Optional<List<EmployeeDetails>> findByorgId(String orgId);

}
