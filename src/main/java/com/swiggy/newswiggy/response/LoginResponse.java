package com.swiggy.newswiggy.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginResponse {

	private int UserId;
	private String name;
	private String message;
	private String status;
	private int httpCode;
	private String token;
	private long expiresIn;

	public String getToken() {
		return token;
	}
}
