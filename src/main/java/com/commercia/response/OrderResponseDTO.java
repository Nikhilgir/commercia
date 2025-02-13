package com.commercia.response;

import com.commercia.entity.enums.OrderStatus;
import com.commercia.entity.enums.PaymentStatus;

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
