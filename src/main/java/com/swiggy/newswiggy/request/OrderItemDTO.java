package com.swiggy.newswiggy.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrderItemDTO {
	private Integer productId;
	private Integer quantity;
}
