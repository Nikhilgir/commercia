package com.swiggy.newswiggy.response;

import com.swiggy.newswiggy.entity.enums.OrderStatus;
import com.swiggy.newswiggy.entity.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
	private Integer orderId;
	private Double totalAmount;
	private OrderStatus orderStatus;
	private PaymentStatus paymentStatus;
	private AddressResponseDto addresses = new AddressResponseDto();
}
