package com.swiggy.newswiggy.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
	private int restaurantId;
	private String name;
	private String description;
	private String address;
	private String city;
	private String phone;
	private double rating;
	private String openingTime;
	private String closingTime;

}
