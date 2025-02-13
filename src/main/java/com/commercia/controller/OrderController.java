package com.commercia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercia.entity.enums.OrderStatus;
import com.commercia.entity.enums.PaymentStatus;
import com.commercia.request.OrderRequest;
import com.commercia.response.OrderResponseDTO;
import com.commercia.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/place-order")
	public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequest orderRequest) {
		return ResponseEntity.ok(orderService.placeOrder(orderRequest));
	}

	@GetMapping("/get-orders/{userId}")
	public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable("userId") int userId) {
		return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
	}

	@GetMapping(value = "/update-order-status/{orderId}/{status}")
	public String updateOrderStatus(@PathVariable("orderId") int orderId, @PathVariable("status") OrderStatus status) {
		// OrderStatus=>(PENDING, CONFIRMED, PREPARING,
		// OUT_FOR_DELIVERY,DELIVERED,CANCELLED)
		return orderService.updateOrderStatus(orderId, status);
	}

	@GetMapping(value = "/update-payment-status/{orderId}/{status}")
	public String updatePaymentStatus(@PathVariable("orderId") int orderId,
			@PathVariable("status") PaymentStatus status) {
		// PaymentStatus=> (PENDING, PAID, FAILED)
		return orderService.updatePaymentStatus(orderId, status);
	}
}
