package com.revature.exceptions;

public class NegativeNumberException extends RuntimeException {

	public NegativeNumberException() {
		super("Cannot have a negative number");
	}

	public NegativeNumberException(String message) {
		super(message);
	}

	public NegativeNumberException(Throwable cause) {
		super(cause);
	}

	public NegativeNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegativeNumberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
