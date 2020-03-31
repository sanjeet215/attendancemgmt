package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.PayStructure;

@Repository
public interface PayStructureRepository extends  JpaRepository<PayStructure,Long>{
	
	Optional<PayStructure> findByOrgIdAndComponentName(String orgId,String componentName);

	Optional<List<PayStructure>> findByorgId(String orgId);
}
