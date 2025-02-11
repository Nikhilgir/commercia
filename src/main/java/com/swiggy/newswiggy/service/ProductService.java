package com.swiggy.newswiggy.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.swiggy.newswiggy.entity.Products;
import com.swiggy.newswiggy.request.ProductRequest;
import com.swiggy.newswiggy.response.ProductResponse;

public interface ProductService {
	public String saveProduct(ProductRequest product);

	public Page<Products> getProducts(int page, int size);

	public ProductResponse findProductById(int id);

	public List<Products> findAllProductByRestaurantId(int restaurantId);
}
