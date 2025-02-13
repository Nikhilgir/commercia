package com.commercia.service;

import java.util.List;

import com.commercia.entity.Restaurant;
import com.commercia.request.RestaurantRequest;
import com.commercia.response.RestaurantResponse;

public interface RestaurantService {
	public String addRestaurant(RestaurantRequest restaurantRequest);

	public RestaurantResponse getRestaurantById(int restaurantId);

	public List<Restaurant> findRestaurantByCity(String city);
}
