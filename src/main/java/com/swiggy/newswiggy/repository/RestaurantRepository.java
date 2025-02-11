package com.swiggy.newswiggy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiggy.newswiggy.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	public List<Restaurant> findByCity(String city);
}
