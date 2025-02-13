package com.commercia.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.commercia.entity.Products;
import com.commercia.request.ProductRequest;
import com.commercia.response.ProductResponse;

public interface ProductService {
	public String saveProduct(ProductRequest product);

	public Page<Products> getProducts(int page, int size);

	public ProductResponse findProductById(int id);

	public List<Products> findAllProductByRestaurantId(int restaurantId);
}
