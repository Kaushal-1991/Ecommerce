package com.ecommerce.exception;

public class APIException extends RuntimeException{
    public static final long srialVersionUid = 1L;

	public APIException() {
	}
	
	public APIException(String message) {
		super(message);
	}

    
}
