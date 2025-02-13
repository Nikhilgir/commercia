package com.commercia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurants")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
	private int restaurantId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "phone", nullable = false, unique = true, length = 15)
	private String phone;

	@Column(name = "rating", columnDefinition = "DECIMAL(3,2) DEFAULT 0.0")
	private double rating;

	@Column(name = "opening_time")
	private String openingTime;

	@Column(name = "closing_time")
	private String closingTime;
}
