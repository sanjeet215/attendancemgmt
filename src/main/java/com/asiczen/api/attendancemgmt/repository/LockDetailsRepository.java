package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiczen.api.attendancemgmt.model.LockDetails;

public interface LockDetailsRepository extends JpaRepository<LockDetails, Long> {

	Optional<LockDetails> findByOrgIdAndEmpIdAndLockmacid(String orgid, String empid, String macid);

	Optional<List<LockDetails>> findByemail(String emailid);
}
