package com.swiggy.newswiggy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "isveg", nullable = false)
	private boolean isVeg;

	@Column(name = "description")
	private String description;

	@Column(name = "category")
	private String category;

	@Column(name = "rating")
	private float rating;

	@Lob
	@Column(name = "image")
	private byte[] image;
}
