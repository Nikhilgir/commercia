package com.swiggy.newswiggy.exception;

public class InvalidTokenException extends RuntimeException {

	@java.io.Serial
	private static final long serialVersionUID = -5365630128856068164L;

	public InvalidTokenException(String message) {
		super(message);
	}

}
