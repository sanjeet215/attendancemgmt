package com.asiczen.api.attendancemgmt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Employee;
import com.asiczen.api.attendancemgmt.model.LockDetails;
import com.asiczen.api.attendancemgmt.payload.response.LockResponse;
import com.asiczen.api.attendancemgmt.repository.EmployeeRepository;
import com.asiczen.api.attendancemgmt.repository.LockDetailsRepository;

@Service
public class LockService {

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	LockDetailsRepository lockRepo;

	/* Post data to database */

	public LockDetails postDataToRepo(LockDetails lockDetails) {

		/* Step 1 : Check if emp id and org id combination is present in database */
		Optional<Employee> employee = empRepo.findByOrgIdAndEmpId(lockDetails.getOrgId(), lockDetails.getEmpId());

		if (!employee.isPresent()) {
			throw new ResourceNotFoundException(
					"Employee not Found with empId and OrgID " + lockDetails.getOrgId() + "_" + lockDetails.getEmpId());
		}

		/*
		 * Step 2 check if employee is already registered with another emp id using emil
		 * id
		 */

		Optional<List<LockDetails>> lockrecords = lockRepo.findByemail(lockDetails.getEmail());

		if (lockrecords.isPresent()) {

			List<String> distinctEmpidsbyEmailid = lockrecords.get().stream().map(item -> item.getEmpId())
					.collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

			long distinctEmpidcount = distinctEmpidsbyEmailid.stream().count();

			if (distinctEmpidcount > 1) {
				throw new ResourceAlreadyExistException("Employee id is already registerd with another emp emailid.");
			} else {
				if (distinctEmpidsbyEmailid.get(0).equalsIgnoreCase(lockDetails.getEmpId())) {
					// Should be ok , can register as incoming emp id and db emp (already registered
					// record) id are same.
				} else {
					throw new ResourceAlreadyExistException(
							"Email id is already registered with empid " + distinctEmpidsbyEmailid.get(0));
				}
			}

		} else {
			// skip
		}

		/* Step 3 check if registered email ids are unique against any other empid */

		Optional<List<LockDetails>> records = lockRepo.findByempId(lockDetails.getEmpId());

		if (records.isPresent()) {

			List<String> distinctEmpidsbyEmailid = records.get().stream().map(item -> item.getEmail())
					.collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

			long distinctEmailidCount = distinctEmpidsbyEmailid.stream().count();

			if (distinctEmailidCount > 1) {
				throw new ResourceAlreadyExistException("Email id already registered with another empid");
			} else {
				if (distinctEmpidsbyEmailid.get(0).equalsIgnoreCase(lockDetails.getEmail())) {

				} else {
					throw new ResourceAlreadyExistException(
							"Email id is already registered with empid -->" + lockDetails.getEmpId());
				}
			}

		} else {
			// proceed to save records
		}

		Optional<LockDetails> lockdetails = lockRepo.findByOrgIdAndEmpIdAndLockmacid(lockDetails.getOrgId(),
				lockDetails.getEmpId(), lockDetails.getLockmacid());

		if (lockdetails.isPresent()) {
			throw new ResourceAlreadyExistException("Record already present in database.");
		}

		return lockRepo.save(lockDetails);
	}

	public LockResponse getMacidsbyEmailId(String emailid) {

		Optional<List<LockDetails>> dbResponse = lockRepo.findByemail(emailid);

		if (!dbResponse.isPresent()) {
			throw new ResourceNotFoundException("Resource not present with supplied email");
		} else {

			LockResponse response = new LockResponse();

			response.setEmailId(dbResponse.get().get(0).getEmail());
			response.setOrgId(dbResponse.get().get(0).getOrgId());
			response.setEmpId(dbResponse.get().get(0).getEmpId());

			List<String> lockmacid = new ArrayList<>();

			dbResponse.get().forEach(item -> {
				if (!item.getLockmacid().isEmpty()) {
					lockmacid.add(item.getLockmacid());
				}
			});

			response.setLockmacid(lockmacid);

			return response;
		}

	}

}
