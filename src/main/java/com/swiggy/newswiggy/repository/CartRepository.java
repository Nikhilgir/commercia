package com.swiggy.newswiggy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swiggy.newswiggy.entity.Cart;
import com.swiggy.newswiggy.repository.projections.CartProductProjection;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	@Query(value = "SELECT c.cart_id AS cartId, p.product_id AS productId, p.name AS productName, "
			+ "p.price AS price, p.isveg AS isVeg, p.description AS productDescription, "
			+ "p.category AS productCategory, p.rating AS productRating, p.image AS productImage, c.quantity AS quantity "
			+ "FROM Product_cart c " + "JOIN products p ON c.product_id = p.product_id "
			+ "WHERE c.user_id = :userId", nativeQuery = true)
	List<CartProductProjection> findCartItemsByUserId(@Param("userId") Integer userId);

	Optional<Cart> findByUserIdAndProductId(int userId, int productId);

}
