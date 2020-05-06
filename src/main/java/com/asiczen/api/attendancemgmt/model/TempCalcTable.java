package com.asiczen.api.attendancemgmt.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tempcalctable")
public class TempCalcTable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String empid;
	private LocalDateTime tempTime;
	private String type;
	private int effectiveworkinhour;

	public TempCalcTable() {
		super();
	}

	public TempCalcTable(String empid, LocalDateTime localDateTime, String type, int effectiveworkinhour) {
		super();
		this.empid = empid;
		this.tempTime = localDateTime;
		this.type = type;
		this.effectiveworkinhour = effectiveworkinhour;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public LocalDateTime getTempTime() {
		return tempTime;
	}

	public void setTempTime(LocalDateTime tempTime) {
		this.tempTime = tempTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEffectiveworkinhour() {
		return effectiveworkinhour;
	}

	public void setEffectiveworkinhour(int effectiveworkinhour) {
		this.effectiveworkinhour = effectiveworkinhour;
	}

	@Override
	public String toString() {
		return "TempCalcTable [empid=" + empid + ", tempTime=" + tempTime + ", type=" + type + ", effectiveworkinhour="
				+ effectiveworkinhour + "]";
	}

}
