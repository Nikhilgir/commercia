package com.swiggy.newswiggy.exception;

public class UnauthorizedUserException extends RuntimeException {

	@java.io.Serial
	private static final long serialVersionUID = -5365630128856068164L;

	public UnauthorizedUserException(String message) {
		super(message);

	}

}
