package com.commercia.service;

import java.util.List;

import com.commercia.entity.enums.OrderStatus;
import com.commercia.entity.enums.PaymentStatus;
import com.commercia.request.OrderRequest;
import com.commercia.response.OrderResponseDTO;

public interface OrderService {
	OrderResponseDTO placeOrder(OrderRequest orderRequest);

	String updateOrderStatus(int orderId, OrderStatus status);

	String updatePaymentStatus(int orderId, PaymentStatus status);

	List<OrderResponseDTO> getOrdersByUserId(int userId);
}