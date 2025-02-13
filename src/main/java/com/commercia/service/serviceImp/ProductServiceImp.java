package com.commercia.service.serviceImp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.commercia.entity.Products;
import com.commercia.entity.Restaurant;
import com.commercia.exception.ResourceNotFound;
import com.commercia.repository.ProductRepository;
import com.commercia.repository.RestaurantRepository;
import com.commercia.request.ProductRequest;
import com.commercia.response.ProductResponse;
import com.commercia.service.ProductService;

@Service
public class ProductServiceImp implements ProductService {

	private ProductRepository productRepository;
	private RestaurantRepository restaurantRepository;

	@Autowired
	public ProductServiceImp(ProductRepository productRepository, RestaurantRepository restaurantRepository) {
		this.productRepository = productRepository;
		this.restaurantRepository = restaurantRepository;
	}

	@Override
	public String saveProduct(ProductRequest productData) {

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
		Optional<Restaurant> restaurant = restaurantRepository.findById(productData.getRestaurantId());
		if (restaurant.isPresent()) {
			product.setRestaurant(restaurant.get());
			productRepository.save(product);
		} else {
			throw new ResourceNotFound("Restaurant not found");
		}
		return "Product Successfully added";
	}

	@Override
	public ProductResponse findProductById(int id) {

		Products product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product Not Found"));
		ProductResponse productResponse = new ProductResponse();
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		productResponse.setVeg(product.isVeg());
		productResponse.setDescription(product.getDescription());
		productResponse.setImage(product.getImage());
		productResponse.setRestaurantId(product.getRestaurant().getRestaurantId());
		productResponse.setRestaurantName(product.getRestaurant().getName());
		return productResponse;
	}

	@Override
	public Page<Products> getProducts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Products> findAllProductByRestaurantId(int restaurantId) {
		return productRepository.findProductsByRestaurantId(restaurantId);
	}
}
