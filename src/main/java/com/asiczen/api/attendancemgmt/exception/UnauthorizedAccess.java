package com.asiczen.api.attendancemgmt.exception;

public class UnauthorizedAccess extends RuntimeException {

	private static final long serialVersionUID = -755927677305904808L;
	
	public UnauthorizedAccess() {
		super();
	}

	public UnauthorizedAccess(String message) {
		super(message);
	}

	public UnauthorizedAccess(String message, Throwable cause) {
		super(message, cause);
	}

}
