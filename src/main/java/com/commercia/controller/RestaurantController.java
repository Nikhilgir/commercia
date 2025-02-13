package com.commercia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercia.entity.Restaurant;
import com.commercia.request.RestaurantRequest;
import com.commercia.response.RestaurantResponse;
import com.commercia.service.RestaurantService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {
	private RestaurantService restaurantService;

	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	@PostMapping(value = "/add-restaurant")
	public String addNewRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
		return restaurantService.addRestaurant(restaurantRequest);
	}

	@GetMapping(value = "get-restaurant/{restaurantId}")
	public RestaurantResponse getRestaurantById(@PathVariable("restaurantId") int restaurantId) {
		return restaurantService.getRestaurantById(restaurantId);
	}

	@GetMapping(value = "find-restaurant-by-city/{city}")
	public List<Restaurant> getRestaurantById(@PathVariable("city") String city) {
		return restaurantService.findRestaurantByCity(city);
	}
}
