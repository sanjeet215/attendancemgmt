package com.asiczen.api.attendancemgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiczen.api.attendancemgmt.model.Moderator;

public interface ModeratorRepository extends JpaRepository<Moderator,Long>{

	Optional<Moderator> findByOrgIdAndStatus(String orgId,boolean status);
	
	Optional<Moderator> findByUsernameAndStatus(String username,boolean status);
}
