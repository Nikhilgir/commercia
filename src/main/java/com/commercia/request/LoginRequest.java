package com.commercia.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

	@Email(message = "Please Enter a valid email")
	@NotBlank(message = "Please enter Email")
	private String userName;

	@NotBlank(message = "Please enter password")
	@Size(min = 6, max = 12, message = "The length of password must be between 6 and 12 characters.")
	private String password;
}
