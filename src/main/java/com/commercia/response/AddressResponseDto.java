package com.commercia.response;

import com.commercia.entity.Country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {
	private int id;
	private String houseNo;

	private String landMark;

	private String city;

	private String state;

	private int zipCode;
	private Country country;
}
