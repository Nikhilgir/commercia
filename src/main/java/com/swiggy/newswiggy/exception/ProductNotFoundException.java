package com.swiggy.newswiggy.exception;

public class ProductNotFoundException extends RuntimeException {

	@java.io.Serial
	private static final long serialVersionUID = -5365630128856068164L;

	public ProductNotFoundException(String message) {
		super(message);
	}
}
