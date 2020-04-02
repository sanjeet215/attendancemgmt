package com.asiczen.api.attendancemgmt.exception;

public class DateFormatException extends RuntimeException{
	
	private static final long serialVersionUID = -8591749273328190904L;
	
	public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

}
