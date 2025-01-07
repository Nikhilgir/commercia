package com.swiggy.newswiggy.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

	private String name;
	private float price;
	private String description;
	private boolean isVeg;
	private byte[] image;
}
