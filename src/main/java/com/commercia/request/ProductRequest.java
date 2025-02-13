package com.commercia.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
	@NotBlank
	@Size(min = 2, max = 20, message = "The length of first name must be between 2 and 20 characters.")
	private String name;

	@Min(1)
	private float price;

	@Size(max = 1000, message = "The length of first name must be between 2 and 20 characters.")
	private String description;

	@NotNull(message = "isVeg must not be null")
	private boolean isVeg;

	@NotBlank
	@Size(min = 2, max = 50, message = "The length of first name must be between 2 and 20 characters.")
	private String category;

	@NotNull(message = "Image must not be null")
	private MultipartFile image;

	private int restaurantId;

}