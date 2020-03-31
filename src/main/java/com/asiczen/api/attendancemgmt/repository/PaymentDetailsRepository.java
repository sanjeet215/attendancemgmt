package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiczen.api.attendancemgmt.model.PaymentDetails;

public interface PaymentDetailsRepository extends  JpaRepository<PaymentDetails,Long>{

	Optional<PaymentDetails> findByOrgIdAndEmpIdAndComponentName(String orgId,String empId,String componenetName);
	
	Optional<List<PaymentDetails>> findByOrgIdAndEmpId(String orgId,String empId);
	
}
