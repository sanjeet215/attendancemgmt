package com.asiczen.api.attendancemgmt.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4761344996270489047L;
	
	public ResourceNotFoundException() {
        super();
    }
	
	public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
