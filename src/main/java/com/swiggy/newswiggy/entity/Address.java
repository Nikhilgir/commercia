package com.swiggy.newswiggy.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Address {

	@JsonProperty("house_no")
	private String houseNo;

	@JsonProperty("land_mark")
	private String landMark;

	@JsonProperty("city")
	private String city;

	@JsonProperty("state")
	private String state;

	@JsonProperty("zipCode")
	private int zipCode;
}
