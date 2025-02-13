package com.commercia.service;

import java.util.List;

import com.commercia.response.CartProductResponse;

public interface CartService {
	public List<CartProductResponse> getToCartItemByUserId(int userId);

	public String addToCart(int userId, int productId);

	public String reduceProductQuantity(int userId, int productId);
}
