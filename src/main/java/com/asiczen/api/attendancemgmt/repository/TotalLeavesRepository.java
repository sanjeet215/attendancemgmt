package com.asiczen.api.attendancemgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiczen.api.attendancemgmt.model.TotalLeaves;

public interface TotalLeavesRepository extends JpaRepository<TotalLeaves,Long>{

	Optional<TotalLeaves> findByorgId(String orgId);
	
	Optional<TotalLeaves> findByOrgIdAndStatus(String orgId,boolean status);
}
