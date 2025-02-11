package com.swiggy.newswiggy.exception;

public class UserNameNotFoundException extends RuntimeException {

	@java.io.Serial
	static final long serialVersionUID = -3387516993124229948L;

	public UserNameNotFoundException(String message) {
		super(message);

	}
}
