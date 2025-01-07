package com.swiggy.newswiggy.service;

import org.springframework.data.domain.Page;

import com.swiggy.newswiggy.entity.Products;
import com.swiggy.newswiggy.request.ProductRequest;

public interface ProductService {
	public void saveProduct(ProductRequest product);

	public Page<Products> getProducts(int page, int size);

	public Products findProductById(int id);
}
