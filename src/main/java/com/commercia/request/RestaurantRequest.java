package com.commercia.request;

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
public class RestaurantRequest {
	private String name;
	private String description;
	private String address;
	private String city;
	private String phone;
	private double rating;
	private String openingTime;
	private String closingTime;
}
