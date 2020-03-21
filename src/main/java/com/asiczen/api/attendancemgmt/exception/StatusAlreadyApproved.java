package com.asiczen.api.attendancemgmt.exception;

public class StatusAlreadyApproved extends RuntimeException{

	private static final long serialVersionUID = 3996461959879289548L;
	
	public StatusAlreadyApproved() {
        super();
    }
	
	public StatusAlreadyApproved(String message) {
        super(message);
    }

    public StatusAlreadyApproved(String message, Throwable cause) {
        super(message, cause);
    }
	

}
