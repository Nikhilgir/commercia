package com.swiggy.newswiggy.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponse {
	private Integer cartId;
	private Integer productId;
	private String productName;
	private Double productPrice;
	private Boolean isVeg;
	private String productDescription;
	private String productCategory;
	private Double productRating;
	private byte[] ProductImage;
	private Integer quantity;

}
