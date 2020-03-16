package com.asiczen.api.attendancemgmt.exception;

public class SomeInternalException extends RuntimeException{

	private static final long serialVersionUID = -8145542184238324863L;
	
	public SomeInternalException() {
        super();
    }
	
	public SomeInternalException(String message) {
        super(message);
    }

    public SomeInternalException(String message, Throwable cause) {
        super(message, cause);
    }

}
