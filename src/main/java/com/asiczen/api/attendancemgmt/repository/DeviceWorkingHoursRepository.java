package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiczen.api.attendancemgmt.model.DeviceWorkingHours;

public interface DeviceWorkingHoursRepository extends JpaRepository<DeviceWorkingHours, Long> {

	Optional<List<DeviceWorkingHours>> findByOrgIdAndEmpId(String orgid,String empid);
	
	Optional<List<DeviceWorkingHours>> findByOrgIdAndEmpIdAndMonthAndYear(String orgid,String empid,String month,String year);
	
}
