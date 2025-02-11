package com.swiggy.newswiggy.repository.projections;

public interface CartProductProjection {
	Integer getCartId();

	Integer getProductId();

	String getProductName();

	Double getPrice();

	Boolean getIsVej();

	String getProductDescription();

	String getProductCategory();

	Double getProductRating();

	byte[] getProductImage();

	Integer getQuantity();
}
