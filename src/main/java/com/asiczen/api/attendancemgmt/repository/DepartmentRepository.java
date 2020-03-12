package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{

	boolean existsByorgId(String orgId);	
	
	Optional<Department> findBydeptId(Long deptId);
	
	Optional<List<Department>> findBydeptName(String deptName);
	
	Optional<List<Department>> findByOrgIdAndStatus(String orgId,boolean status);
	
	Optional<Department> findByDeptIdAndOrgIdAndStatus(Long deptId,String orgId,boolean status);
	
	Optional<Long> countByOrgIdAndStatus(String orgId,boolean status);
	
}
