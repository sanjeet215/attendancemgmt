package com.asiczen.api.attendancemgmt.utils;


import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.model.Empinout;
import com.asiczen.api.attendancemgmt.model.TempCalcTable;
import com.asiczen.api.attendancemgmt.repository.DeviceWorkingHoursRepository;
import com.asiczen.api.attendancemgmt.repository.EmpinoutRepository;
import com.asiczen.api.attendancemgmt.repository.TempCalcTableRepository;

@Service
public class CalulateWorkingHours {

	private static final Logger logger = LoggerFactory.getLogger(CalulateWorkingHours.class);

	private static final String CONST_IN = "in";
	private static final String CONST_OUT = "out";

	@Autowired
	EmpinoutRepository repo;

	@Autowired
	DeviceWorkingHoursRepository destRepo;

	@Autowired
	TempCalcTableRepository tempRepo;

	public void exactworkinghours() {

		// Step 1 : Get all unprocessed records.
		// Step 2 : Sort them based on time stamp
		// Step 3 : Calculate exact working hours
		// Step 4 : mark the records as processed.

		List<String> empList = new ArrayList<>();
		Optional<List<Empinout>> dataList = repo.findByActiveOrderByTimeStampAsc(true);

		if (dataList.isPresent()) {
			dataList.get().forEach(item -> {
				empList.add(item.getEmpId());
			});
		}

		empList.stream().distinct().collect(Collectors.toList()).forEach(item -> {
			repo.findByActiveAndEmpIdOrderByTimeStampAsc(true, item).ifPresent(lineItem -> {
				lineItem.forEach(record -> {
					if (record.getType().equalsIgnoreCase(CONST_IN)) {
						if (!tempRepo.existsByempid(record.getEmpId())) {
							tempRepo.save(new TempCalcTable(record.getEmpId(), record.getTimeStamp(), record.getType(), 0));
						} else {
							int minutes = (int) MINUTES.between(record.getTimeStamp(),tempRepo.findByempid(record.getEmpId()).get().getTempTime());
							
							TempCalcTable tempRecord = tempRepo.findByempid(record.getEmpId()).get();
							
							tempRecord.setEffectiveworkinhour(tempRecord.getEffectiveworkinhour()+minutes);
							tempRecord.setTempTime(record.getTimeStamp());
							
							tempRepo.save(tempRecord);
						}
							
					}
				});
			});
		});

	}
}
