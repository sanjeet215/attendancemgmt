package com.asiczen.api.attendancemgmt.payload.response;

import java.time.LocalDate;

public class CalculatedResult {

	private LocalDate date;
	private boolean statusbyDevice;
	private boolean statusByLeave;
	private boolean compareData;

	public CalculatedResult() {
		super();
	}

	public CalculatedResult(LocalDate date, boolean statusbyDevice, boolean statusByLeave, boolean compareData) {
		super();
		this.date = date;
		this.statusbyDevice = statusbyDevice;
		this.statusByLeave = statusByLeave;
		this.compareData = compareData;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isStatusbyDevice() {
		return statusbyDevice;
	}

	public void setStatusbyDevice(boolean statusbyDevice) {
		this.statusbyDevice = statusbyDevice;
	}

	public boolean isStatusByLeave() {
		return statusByLeave;
	}

	public void setStatusByLeave(boolean statusByLeave) {
		this.statusByLeave = statusByLeave;
	}

	public boolean isCompareData() {
		return compareData;
	}

	public void setCompareData(boolean compareData) {
		this.compareData = compareData;
	}

	@Override
	public String toString() {
		return "CalculatedResult [date=" + date + ", statusbyDevice=" + statusbyDevice + ", statusByLeave="
				+ statusByLeave + ", compareData=" + compareData + "]";
	}

}
