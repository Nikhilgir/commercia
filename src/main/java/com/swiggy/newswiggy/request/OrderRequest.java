package com.swiggy.newswiggy.request;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class OrderRequest {
	private int userId;
	private int restaurantId;
	private int addressId;
	private List<OrderItemDTO> items;
}
