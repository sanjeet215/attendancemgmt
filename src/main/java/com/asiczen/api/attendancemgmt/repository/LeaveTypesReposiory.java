package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.LeaveTypes;

@Repository
public interface LeaveTypesReposiory extends JpaRepository<LeaveTypes, Long> {

	Optional<List<LeaveTypes>> findByorgIdIs(String orgId);
	
	Optional<List<LeaveTypes>> findByLeaveTypeNameAndOrgIdAndStatus(String leaveType,String ogid,boolean status);
	
	Optional<LeaveTypes> findByLeaveTypeNameAndOrgId(String leaveType,String ogid);

	Optional<List<LeaveTypes>> findByOrgIdAndStatus(String orgid,boolean status);
	
	void deleteByOrgId(String orgId);
	
}
