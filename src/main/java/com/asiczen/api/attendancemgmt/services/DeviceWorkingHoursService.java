package com.asiczen.api.attendancemgmt.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.model.AppliedLeaves;
import com.asiczen.api.attendancemgmt.model.DeviceWorkingHours;
import com.asiczen.api.attendancemgmt.payload.response.CalculatedResult;
import com.asiczen.api.attendancemgmt.payload.response.DeviceWorkingHoursResponse;
import com.asiczen.api.attendancemgmt.payload.response.ReconResults;
import com.asiczen.api.attendancemgmt.repository.DeviceWorkingHoursRepository;
import com.asiczen.api.attendancemgmt.utils.UtilityService;

@Service
public class DeviceWorkingHoursService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceWorkingHoursService.class);

	@Autowired
	DeviceWorkingHoursRepository deviceRepo;

	@Autowired
	UtilityService utilService;

	@Autowired
	ApplyServiceImpl leaveService;

	/* device working hours by orgid and emp id */
	public List<DeviceWorkingHoursResponse> getworkinghoursperDevice(String orgid, String empid) {

		Optional<List<DeviceWorkingHours>> workinhoursList = deviceRepo.findByOrgIdAndEmpId(orgid, empid);

		if (!workinhoursList.isPresent()) {
			return null;
		} else {

			List<DeviceWorkingHoursResponse> response = new ArrayList<>();

			workinhoursList.get().forEach(item -> {
				DeviceWorkingHoursResponse record = new DeviceWorkingHoursResponse();

				record.setCalculatedhours((float) item.getCalculatedhours() / 60);
				record.setDate(item.getDate());
				record.setEmpId(item.getEmpId());
				record.setMonth(item.getMonth());
				record.setOrgId(item.getOrgId());
				record.setRecordId(item.getRecordId());
				record.setYear(item.getYear());
				response.add(record);
			});

			return response;
		}

	}

	/* device working hours by orgid, empid,month and year */

	public List<DeviceWorkingHours> getworkinghoursperDevicebyMonthandYear(String orgid, String empid, String month,
			String year) {

		Optional<List<DeviceWorkingHours>> workinhoursList = deviceRepo.findByOrgIdAndEmpIdAndMonthAndYear(orgid, empid,
				month, year);
		if (!workinhoursList.isPresent()) {
			return null;
		} else {
			return workinhoursList.get();
		}
	}

	/* This method is to compare between leave management system vs device data */

	public ReconResults compareAttendanceData(String orgid, String empid, String month, String year) {

		LocalDate startOfMonth = utilService.getDateBeginingOFMonth(month, year);
		LocalDate endOfMonth = utilService.getDateEndOFMonth(month, year);

		/* Get the data from device data and populate the map */

		LinkedHashMap<LocalDate, Boolean> deviceData = new LinkedHashMap<>();
		populateMap(deviceData, startOfMonth, endOfMonth);

		Optional<List<DeviceWorkingHours>> workinhoursList = deviceRepo.findByOrgIdAndEmpIdAndMonthAndYear(orgid, empid,
				month, year);

		if (Boolean.FALSE.equals(workinhoursList.isPresent())) {
		} else {
			workinhoursList.get().forEach(item -> {

				LocalDate keyDate = item.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				if (Boolean.FALSE.equals(deviceData.get(keyDate))) {
					deviceData.put(keyDate, true);
				}

			});
		}

		/* Get Data from leave system and populate data */

		LinkedHashMap<LocalDate, Boolean> leaveData = new LinkedHashMap<>();
		populateMap(leaveData, startOfMonth, endOfMonth);

		List<AppliedLeaves> leaveList = leaveService.getApprovedLeaves(orgid, empid, "approved");

		if (!leaveList.isEmpty()) {
			leaveList.forEach(item -> {
				populateLeaveData(item.getFromDate(), item.getToDate(), leaveData);
			});
		}

		System.out.println("As per Leave Mgmt system");

		leaveData.forEach((k, v) -> {
			logger.info(k + "--->" + v);
		});

		logger.info("As per Device Mgmt system");

		deviceData.forEach((k, v) -> {
			logger.debug(k.toString(), v);
			logger.info(k + "--->" + v);
		});

		/* now populate the object calculatedLeaves. This is reconciliation view. */

		List<CalculatedResult> resultset = new ArrayList<>();

		deviceData.forEach((k, v) -> {
			CalculatedResult result = new CalculatedResult();

			result.setDate(k);
			result.setStatusbyDevice(v);
			result.setStatusByLeave(leaveData.getOrDefault(k, true));
			result.setCompareData(v == leaveData.getOrDefault(k, true));

			resultset.add(result);
		});

		/* Remove after testing */
		resultset.forEach(item -> {
			logger.info(item.toString());
		});

		ReconResults finaldata = new ReconResults();
		finaldata.setEmpId(empid);
		finaldata.setOrgId(orgid);
		finaldata.setResults(resultset);

		return finaldata;

	}

	private void populateLeaveData(LocalDate fromDate, LocalDate toDate, LinkedHashMap<LocalDate, Boolean> leaveData) {

		for (LocalDate date = fromDate; date.isBefore(toDate); date = date.plusDays(1)) {
			leaveData.put(date, true);
		}

		leaveData.put(toDate, true);
	}

	private void populateMap(LinkedHashMap<LocalDate, Boolean> dateMap, LocalDate startOfMonth, LocalDate endOfMonth) {

		for (LocalDate date = startOfMonth; date.isBefore(endOfMonth); date = date.plusDays(1)) {
			dateMap.put(date, false);
		}

		dateMap.put(endOfMonth, false);

	}
}
