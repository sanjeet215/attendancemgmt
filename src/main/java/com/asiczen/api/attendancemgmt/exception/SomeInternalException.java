package com.asiczen.api.attendancemgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
