package com.asiczen.api.attendancemgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	boolean existsByorgId(String orgId);
	
	Optional<Organization> findById(long id);
	
	Optional<Organization> findByorgId(String orgId);
}
