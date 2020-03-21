package com.asiczen.api.attendancemgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.AppliedLeaves;

@Repository
public interface ApplyLeaveRepository extends JpaRepository<AppliedLeaves,Long>{

	/*Employee Specific by Organization Id and Employee id*/
	Optional<AppliedLeaves> findByOrgIdAndEmpId(String orgId,String empId);
	
	/*Employee Specific by Organization Id , Employee id , Status*/
	Optional<AppliedLeaves> findByOrgIdAndEmpIdAndStatus(String orgId,String empId,String status);
	
	/*This is for Admin and Moderator organization wise*/
	Optional<AppliedLeaves> findByOrgIdAndStatus(String orgId,String status);
}
