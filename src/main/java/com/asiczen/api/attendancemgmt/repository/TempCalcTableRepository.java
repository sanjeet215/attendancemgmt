package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.TempCalcTable;

@Repository
public interface TempCalcTableRepository extends JpaRepository<TempCalcTable, Long>{

	Optional<TempCalcTable> findByempid(String empId);
	
	boolean existsByempid(String empid);
}
