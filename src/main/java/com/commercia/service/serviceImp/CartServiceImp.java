package com.commercia.service.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.commercia.entity.Cart;
import com.commercia.entity.Products;
import com.commercia.entity.User;
import com.commercia.exception.ResourceNotFound;
import com.commercia.repository.CartRepository;
import com.commercia.repository.ProductRepository;
import com.commercia.repository.UserRepository;
import com.commercia.repository.projections.CartProductProjection;
import com.commercia.response.CartProductResponse;
import com.commercia.service.CartService;

@Service
public class CartServiceImp implements CartService {

	private CartRepository cartRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;

	public CartServiceImp(CartRepository cartRepository, ProductRepository productRepository,
			UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<CartProductResponse> getToCartItemByUserId(int userId) {

		List<CartProductProjection> cartItem = cartRepository.findCartItemsByUserId(userId);
		return cartItem.stream()
				.map(e -> new CartProductResponse(e.getCartId(), e.getProductId(), e.getProductName(), e.getPrice(),
						e.getIsVej(), e.getProductDescription(), e.getProductCategory(), e.getProductRating(),
						e.getProductImage(), e.getQuantity()))
				.toList();

	}

	@Override
	public String addToCart(int userId, int productId) {
		Cart cart;
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User Not Found"));
		Products product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFound("Product Not Found"));

		Optional<Cart> presentProduct = cartRepository.findByUserIdAndProductId(userId, productId);
		if (presentProduct.isPresent()) {
			cart = presentProduct.get();
			cart.setQuantity(cart.getQuantity() + 1);
			cartRepository.save(cart);
		} else {
			cart = new Cart();
			cart.setUser(user);
			cart.setProduct(product);
			cart.setQuantity(1);

			cartRepository.save(cart);
		}
		return "Product added to cart Successfully, productId= " + cart.getProduct().getId() + ", Quantity= "
				+ cart.getQuantity();

	}

	@Override
	public String reduceProductQuantity(int userId, int productId) {
		Optional<Cart> product = cartRepository.findByUserIdAndProductId(userId, productId);

		if (product.isPresent()) {
			Cart cartProduct = product.get();
			if (cartProduct.getQuantity() > 1) {
				cartProduct.setQuantity(cartProduct.getQuantity() - 1);
				cartRepository.save(cartProduct);
				return "Product quantity reduced successfully, productId= " + cartProduct.getProduct().getId()
						+ ", Quantity= " + cartProduct.getQuantity();
			} else {
				cartRepository.delete(cartProduct);
				return "product removed from cart";
			}
		} else {
			return "Product not found";
		}
	}

}
