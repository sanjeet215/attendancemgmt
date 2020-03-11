package com.asiczen.api.attendancemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "filetable")
public class FileProp extends AuditModel{

	private static final long serialVersionUID = 6450986469603637211L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(nullable = false)
	String fileName;
	
	@Column(nullable = false)
	String filePath;
	
	@Column(nullable = false)
	int size;
	
	@Column(nullable = false)
	boolean active;

	public FileProp() {
		super();
	}

	public FileProp(Long id, String fileName, String filePath, int size, boolean active) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.filePath = filePath;
		this.size = size;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}

