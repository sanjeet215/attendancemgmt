package com.asiczen.api.attendancemgmt.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.AppliedLeaves;
import com.asiczen.api.attendancemgmt.model.LeaveTypes;
import com.asiczen.api.attendancemgmt.payload.response.LeaveBalance;
import com.asiczen.api.attendancemgmt.repository.ApplyLeaveRepository;
import com.asiczen.api.attendancemgmt.repository.LeaveTypesReposiory;

@Service
public class ApplyServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(ApplyServiceImpl.class);

	@Autowired
	ApplyLeaveRepository appliedLeavesRepo;
	
	@Autowired
	LeaveTypesReposiory leaveRepository;

	public AppliedLeaves postLeaves(AppliedLeaves appliedLeave) {

		int quantity = 0;

		System.out.println("From Date -->" + appliedLeave.getFromDate());
		System.out.println("To Date --> " + appliedLeave.getToDate());

		for (LocalDate date = appliedLeave.getFromDate(); date.isBefore(appliedLeave.getToDate()); date = date.plusDays(1)) {

			System.out.println("Looping" + date.getDayOfWeek());

			if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
				logger.info(date.toString() + "Saturday is not a workingday");
			} else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				logger.info(date.toString() + "Sunday is not a workingday");
			} else {
				quantity = quantity + 1;
			}
		}

		if (appliedLeave.getToDate().getDayOfWeek() == DayOfWeek.SATURDAY) {

		} else if (appliedLeave.getToDate().getDayOfWeek() == DayOfWeek.SUNDAY) {

		} else {
			quantity = quantity + 1;
		}

		appliedLeave.setQuantity(quantity);

		return appliedLeavesRepo.save(appliedLeave);
	}
	
	
	
	/* Approval from Admin or User can cancel it's own request*/
	public AppliedLeaves updatestatus(AppliedLeaves appliedLeave) {
		
		// 1. Find Request from database
		
		Optional<AppliedLeaves> leave = appliedLeavesRepo.findById(appliedLeave.getId());
				//appliedLeavesRepo.findByOrgIdAndEmpIdAndStatus(appliedLeave.getOrgId(), appliedLeave.getEmpId(), appliedLeave.getStatus());
		
		if(!leave.isPresent()) {
			throw new ResourceNotFoundException("Request not found to update. "+ leave.get().getEmpId() +"  "+ leave.get().getId());
		}
		
		if(appliedLeave.getStatus().equalsIgnoreCase("APPROVED")) {
			//Post an Enter to HistoryTable
		}
		
		leave.get().setStatus(appliedLeave.getStatus());
		
		return appliedLeavesRepo.save(leave.get());
		
//		if(leave.get().getStatus().equalsIgnoreCase("APPROVED")) {
//			throw new StatusAlreadyApproved("Leave Request is already in approved status");
//		} else if(leave.get().getStatus().equalsIgnoreCase("PENDING")) {
//			
//		} else if(leave.get().getStatus().equalsIgnoreCase("CANCELED")) {
//			
//		}
		
	}
	
	
	public List<AppliedLeaves> getLeaveswithStatus(String orgId,String empId,String status){
		
		Optional<List<AppliedLeaves>> leaves = appliedLeavesRepo.findByOrgIdAndEmpIdAndStatus(orgId, empId, status);
		
		if(!leaves.isPresent()) {
			throw new ResourceNotFoundException("Request Not Found with following criteria. orgId: "+orgId+ " empId: "+empId+ " status:"+ status);
		}
		
		//appliedLeavesRepo.findLeaveHistory();
		
		return leaves.get();
		
	}
	
	/* My Pending approvals screen. This is to show leaves which are pending to be approved */

	public List<AppliedLeaves> getLeaveswithOrgandStatus(String orgId,String status){
		
		Optional<List<AppliedLeaves>> leaves = appliedLeavesRepo.findByOrgIdAndStatus(orgId, status);
		
		if(!leaves.isPresent()) {
			throw new ResourceNotFoundException("Request Not Found with following criteria. orgId: "+orgId+ " status:"+ status);
		}
		return leaves.get();
	}
	
	//Get All leave types based on orgId and empId

	public List<AppliedLeaves> getLeaveswithOrgandEmpId(String orgId,String empId){
		
		Optional<List<AppliedLeaves>> leaves = appliedLeavesRepo.findByOrgIdAndEmpId(orgId, empId);
		
		if(!leaves.isPresent()) {
			throw new ResourceNotFoundException("Request Not Found with following criteria. orgId: "+orgId+ " empId:"+ empId);
		}
		return leaves.get();
	}
	
	//Get Employee specific leave balances
	public LeaveBalance getEmpLeaveBalance(String orgId,String empId) {
		
		HashMap<String, Double> mapOrg = new HashMap<>();
		//HashMap<String, Integer> mapEmp = new HashMap<>();
		
		Optional<List<LeaveTypes>> orgLeaves = leaveRepository.findByOrgIdAndStatus(orgId, true);
		
		if(!orgLeaves.isPresent()) {
			throw new ResourceNotFoundException("Organization with orgid: "+orgId+" is not registered yet.");
		}else {
			orgLeaves.get().forEach(item -> mapOrg.put(item.getLeaveTypeName(),(double)item.getQuantity()));
		}
		
		
		Optional<List<AppliedLeaves>> empLeaves = appliedLeavesRepo.findByOrgIdAndEmpId(orgId, empId);
		
		if(!empLeaves.isPresent()) {
			throw new ResourceNotFoundException("Employee with orgid: "+orgId+ " and empId:"+empId+" is not registered yet.");
		} else {
			
			empLeaves.get().forEach(item->{
				if(item.getStatus().equalsIgnoreCase("pending")||item.getStatus().equalsIgnoreCase("approved")) {
					logger.info("I am in for Loop");
					System.out.println(item.getQuantity());
					if(mapOrg.containsKey(item.getLeaveTypeName())) {
						double quantity = mapOrg.get(item.getLeaveTypeName());
						quantity = quantity-item.getQuantity();
						mapOrg.remove(item.getLeaveTypeName());
						mapOrg.put(item.getLeaveTypeName(), quantity);
					}
				}
			});
		}
		
		logger.debug(mapOrg.toString());
//		System.out.println(mapOrg.toString());
		
		return new LeaveBalance(orgId,empId,mapOrg);
	}
	
	
	
}
