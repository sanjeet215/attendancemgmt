package com.asiczen.api.attendancemgmt.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.model.PayStructure;
import com.asiczen.api.attendancemgmt.repository.PayStructureRepository;

@Service
public class PayStructureServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(PayStructureServiceImpl.class);
	
	@Autowired
	PayStructureRepository paystructRepo;
	
	public List<PayStructure> addSalaryComponents(List<PayStructure> components){
		
		return paystructRepo.saveAll(components);
		
	}
	
	
}