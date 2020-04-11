package com.asiczen.api.attendancemgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	boolean existsByorganizationDisplayName(String orgId);
	
	Optional<Organization> findById(Long id);
	
	Optional<Organization> findByorganizationDisplayName(String orgId);
	
	Optional<Organization> findBycontactEmailId(String emailId);
	
	Optional<Organization> findByorganizationcontact(String phoneNo);
	
	Optional<Organization> findByorganizationDisplayNameAndStatus(String orgId,String status);
	
}
