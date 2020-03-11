package com.asiczen.api.attendancemgmt.exception;

public class ResourceAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = -3028041280405943032L;

//	public ResourceAlreadyExistException(String id) {
//        super("Resource with id already exist in Database : " + id);
//    }

	public ResourceAlreadyExistException() {
		super();
	}

	public ResourceAlreadyExistException(String message) {
		super(message);
	}

	public ResourceAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}
}