package com.swiggy.newswiggy.exception;

public class EmailNotFoundException extends RuntimeException {

	@java.io.Serial
	static final long serialVersionUID = -3387516993124229948L;

	public EmailNotFoundException(String message) {
		super(message);

	}
}
