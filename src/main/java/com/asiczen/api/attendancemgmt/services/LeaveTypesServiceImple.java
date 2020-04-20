package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.LeaveTypes;
import com.asiczen.api.attendancemgmt.repository.LeaveTypesReposiory;

@Service
public class LeaveTypesServiceImple {

	private static final Logger logger = LoggerFactory.getLogger(LeaveTypesServiceImple.class);

	@Autowired
	LeaveTypesReposiory leaveTypeRepo;

	/* 1. check if leaves are already applied */
	/* Duplicate Check*/
	/* Status Check */
	
	public LeaveTypes postLeaves(LeaveTypes leavetype) {
		
		Optional<LeaveTypes> leaveTypeNames = leaveTypeRepo.findByLeaveTypeNameAndOrgId(leavetype.getLeaveTypeName(), leavetype.getOrgId());
		
		if(leaveTypeNames.isPresent())
		    throw new ResourceAlreadyExistException("Leave type already exists."+leavetype.getOrgId()+"_"+leavetype.getLeaveTypeName());
		
		return leaveTypeRepo.save(leavetype);
	}
	
	
	/*Get All leaves present in database for organization*/ 
	
	public List<LeaveTypes> getLeaveTypesByOrganization(String orgId) {

		Optional<List<LeaveTypes>> leavetypes = leaveTypeRepo.findByorgIdIs(orgId);

		if (!leavetypes.isPresent()) {
			logger.error("There are no leave types created for organization");
			throw new ResourceNotFoundException("No leave types are created for organization id: " + orgId);
		}
			

		return leavetypes.get();
	}

	
	public void deleteByOrganization(String orgId) {
		leaveTypeRepo.deleteByOrgId(orgId);
	}
	
	/*Get All leaves present in database by organization and status */
	
//	public List<LeaveTypes> getLeaveTypesByOrganizationAndStatus(String orgid,boolean status){
//		
//		Optional<List<LeaveTypes>> leavetypes = leaveTypeRepo.findByorgIdAndstatus(orgid, status);
//		
//		if (leavetypes.isPresent()) {
//			logger.error("There are no leave types for organization which are active");
//			throw new ResourceNotFoundException("No leave types are present for organization id: " + orgid +" which are active.");
//		}
//		
//		return leavetypes.get();
//		
//	}
	
	
	/*Get All leaves for Super admin */
	public List<LeaveTypes> getAllLeavetypes(){
		return  leaveTypeRepo.findAll();
	}
}
