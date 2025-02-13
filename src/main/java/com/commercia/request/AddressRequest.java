package com.commercia.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AddressRequest {
	
	@NotNull(message = "userId can't be null")
	private int userId;

	@NotBlank(message = "Please enter house No")
	private String houseNo;

	@NotBlank(message = "Please enter land Mark")
	private String landMark;

	@NotBlank(message = "Please enter city")
	private String city;

	@NotBlank(message = "Please enter state")
	private String state;

	@NotBlank(message = "Please enter country name")
	private String country;

	@NotNull(message = "Please enter zip code")
	private int zipCode;
}