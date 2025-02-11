package com.swiggy.newswiggy.service;

import java.util.List;

import com.swiggy.newswiggy.entity.enums.OrderStatus;
import com.swiggy.newswiggy.entity.enums.PaymentStatus;
import com.swiggy.newswiggy.request.OrderRequest;
import com.swiggy.newswiggy.response.OrderResponseDTO;

public interface OrderService {
	OrderResponseDTO placeOrder(OrderRequest orderRequest);

	String updateOrderStatus(int orderId, OrderStatus status);

	String updatePaymentStatus(int orderId, PaymentStatus status);

	List<OrderResponseDTO> getOrdersByUserId(int userId);
}