package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

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

			dbResponse.get().forEach(item -> {
				response.getLockmacid().add(item.getLockmacid());
			});

			return response;
		}

	}

}
