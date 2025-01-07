package com.swiggy.newswiggy.exception;

public class InvalidAgeException extends RuntimeException {

	@java.io.Serial
	private static final long serialVersionUID = -5365630128856068164L;

	public InvalidAgeException(String message) {
		super(message);
	}
}
