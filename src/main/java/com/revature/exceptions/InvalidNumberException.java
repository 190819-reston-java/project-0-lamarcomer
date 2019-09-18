package com.revature.exceptions;

public class InvalidNumberException extends RuntimeException {
	
	public InvalidNumberException() {
		super("Value must be a valid number");
	}
	
	public InvalidNumberException(String message) {
		super(message);
	}
	
	public InvalidNumberException(Throwable cause) {
		super(cause);
	}

	public InvalidNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidNumberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
