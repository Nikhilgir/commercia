package com.commercia.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignupRequest {

	@NotBlank(message = "First Name can not be blank")
	@Size(min = 2, max = 20, message = "The length of first name must be between 2 and 20 characters.")
	private String firstName;

	@NotBlank(message = "Last Name can not be blank")
	@Size(min = 2, max = 20, message = "The length of last name must be between 2 and 20 characters.")
	private String lastName;

	@NotBlank(message = "Password can not be blank")
	@Size(min = 6, max = 12, message = "The length of password must be between 6 and 12 characters.")
	private String password;

	@NotBlank(message = "Please Enter a email")
	@Email(message = "Please Enter a valid email")
	private String email;

	private long phoneNumber;

	@NotEmpty(message = "The gender is required.")
	private String gender;

	@NotNull(message = "Please enter Date of birth")
	@Past(message = "Date of birth should be in past")
	private LocalDate dateOfBirth;

	@Valid
	@NotEmpty(message = "Please fill Address")
	private List<AddressRequest> address;
}