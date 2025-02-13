package com.commercia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercia.request.CartRequest;
import com.commercia.response.CartProductResponse;
import com.commercia.service.CartService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
	private CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping(value = "/add-to-cart")
	public String addToCart(@RequestBody CartRequest cartRequest) {
		return cartService.addToCart(cartRequest.getUserId(), cartRequest.getProductId());
	}

	@GetMapping(value = "/get-cart/{userId}")
	public List<CartProductResponse> getCartByUserId(@PathVariable("userId") int userId) {
		return cartService.getToCartItemByUserId(userId);
	}

	@PostMapping(value = "/reduce-product-quantity/{userId}/{productId}")
	public String reduceProductQuantity(@PathVariable("userId") int userId, @PathVariable("productId") int productId) {
		return cartService.reduceProductQuantity(userId, productId);
	}
}
