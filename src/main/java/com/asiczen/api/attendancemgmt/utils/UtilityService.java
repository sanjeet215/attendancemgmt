package com.asiczen.api.attendancemgmt.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.DateFormatException;

@Service
public class UtilityService {

	private static final Logger logger = LoggerFactory.getLogger(UtilityService.class);

	public LocalDate getDateBeginingOFMonth(String month, String year) {

		String dateString = "01/" + month + "/" + year;

		try {

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
			return LocalDate.parse(dateString, dateTimeFormatter);

		} catch (Exception e) {
			throw new DateFormatException("Incorrect data format exception" + e.getLocalizedMessage());
		}
	}

	public LocalDate getDateEndOFMonth(String month, String year) {

		String dateString = "01/" + month + "/" + year;

		try {

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
			LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);

			return localDate.withDayOfMonth(localDate.lengthOfMonth());

		} catch (Exception e) {
			throw new DateFormatException("Incorrect data format exception" + e.getLocalizedMessage());
		}
	}

	public String getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

}
