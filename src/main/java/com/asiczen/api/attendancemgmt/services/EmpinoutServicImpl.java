package com.asiczen.api.attendancemgmt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.model.Empinout;
import com.asiczen.api.attendancemgmt.repository.EmpinoutRepository;

@Service
public class EmpinoutServicImpl {

	@Autowired
	EmpinoutRepository emplogrepo;
	
	public Empinout storeEmpInOut(Empinout empinout) {
		return emplogrepo.save(empinout);
	}
	
}