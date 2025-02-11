package com.swiggy.newswiggy.service;

import java.util.List;

import com.swiggy.newswiggy.entity.Restaurant;
import com.swiggy.newswiggy.request.RestaurantRequest;
import com.swiggy.newswiggy.response.RestaurantResponse;

public interface RestaurantService {
	public String addRestaurant(RestaurantRequest restaurantRequest);

	public RestaurantResponse getRestaurantById(int restaurantId);

	public List<Restaurant> findRestaurantByCity(String city);
}
