package com.asiczen.api.attendancemgmt.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.DateFormatException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Empinout;
import com.asiczen.api.attendancemgmt.payload.request.EmployeeSwipeRequest;
import com.asiczen.api.attendancemgmt.repository.EmpinoutRepository;

@Service
public class EmpinoutServicImpl {

	private static final Logger logger = LoggerFactory.getLogger(EmpinoutServicImpl.class);

	@Autowired
	EmpinoutRepository emplogrepo;

	public Empinout storeEmpInOut(Empinout empinout) {
		empinout.setActive(true);
		return emplogrepo.save(empinout);
	}

	public Empinout postEmpLoginDetails(EmployeeSwipeRequest request) {

		try {
			String dateTime = request.getSwipeDate() + " " + request.getSwipeTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			LocalDateTime covertedTime = LocalDateTime.parse(dateTime, formatter);

			return emplogrepo
					.save(new Empinout(request.getOrgId(), request.getEmpId(), covertedTime, request.getType(), true));
		} catch (Exception e) {
			throw new DateFormatException(e.getLocalizedMessage());
		}

	}

	public List<Empinout> getEmpLoginDetails(String orgId, String empId) {

		Optional<List<Empinout>> dataList = emplogrepo.findByOrgIdAndEmpIdOrderByTimeStampAsc(orgId, empId);

		if (!dataList.isPresent()) {
			throw new ResourceNotFoundException("No data posted for OrgId: " + orgId + " and empId: " + empId);
		}

		return dataList.get();
	}

}