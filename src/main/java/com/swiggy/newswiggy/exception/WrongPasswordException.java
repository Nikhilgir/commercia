package com.swiggy.newswiggy.exception;

public class WrongPasswordException extends RuntimeException {

	@java.io.Serial
	private static final long serialVersionUID = 2256477558314496007L;

	public WrongPasswordException(String message) {
		super(message);
	}
}
