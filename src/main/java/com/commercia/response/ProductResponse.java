package com.commercia.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

	private int id;
	private String name;
	private double price;
	private String description;
	private boolean isVeg;
	private int restaurantId;
	private String restaurantName;
	private byte[] image;
}