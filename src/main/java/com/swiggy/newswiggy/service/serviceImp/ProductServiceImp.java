package com.swiggy.newswiggy.service.serviceImp;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.swiggy.newswiggy.entity.Products;
import com.swiggy.newswiggy.exception.ProductNotFoundException;
import com.swiggy.newswiggy.repository.ProductRepository;
import com.swiggy.newswiggy.request.ProductRequest;
import com.swiggy.newswiggy.service.ProductService;

@Service
public class ProductServiceImp implements ProductService {

	private ProductRepository productRepository;

	@Autowired
	public ProductServiceImp(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void saveProduct(ProductRequest productData) {

		Products product = new Products();
		product.setName(productData.getName());
		product.setPrice(productData.getPrice());
		product.setVeg(productData.isVeg());
		product.setCategory(productData.getCategory());
		product.setDescription(productData.getDescription());
		product.setRating(0);
		try {
			byte[] image = productData.getImage().getBytes();
			product.setImage(image);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		productRepository.save(product);
	}

	@Override
	public Products findProductById(int id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
	}

	@Override
	public Page<Products> getProducts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findAll(pageable);
	}
}
