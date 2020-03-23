package com.asiczen.api.attendancemgmt.exception;

public class ZipFileCreationException extends RuntimeException {

	private static final long serialVersionUID = -954506086283616963L;

	public ZipFileCreationException() {
		super();
	}

	public ZipFileCreationException(String message) {
		super(message);
	}

	public ZipFileCreationException(String message, Throwable cause) {
		super(message, cause);
	}

}
