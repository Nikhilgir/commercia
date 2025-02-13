package com.commercia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.commercia.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

	@Query("SELECT p FROM Products p WHERE p.restaurant.restaurantId = :restaurantId")
	List<Products> findProductsByRestaurantId(@Param("restaurantId") int restaurantId);

}