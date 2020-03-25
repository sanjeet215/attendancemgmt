package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.asiczen.api.attendancemgmt.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	Optional<List<User>> findByorgId(String orgId);
}
