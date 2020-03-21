package com.asiczen.api.attendancemgmt.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.exception.StatusAlreadyApproved;
import com.asiczen.api.attendancemgmt.model.AppliedLeaves;
import com.asiczen.api.attendancemgmt.repository.ApplyLeaveRepository;

@Service
public class ApplyServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(ApplyServiceImpl.class);

	@Autowired
	ApplyLeaveRepository appliedLeavesRepo;

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
		
		Optional<AppliedLeaves> leave = appliedLeavesRepo.findByOrgIdAndEmpIdAndStatus(appliedLeave.getOrgId(), appliedLeave.getEmpId(), appliedLeave.getStatus());
		
		if(!leave.isPresent()) {
			throw new ResourceNotFoundException("Request not found to update."+ leave.get().getEmpId() + leave.get().getId());
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

}
