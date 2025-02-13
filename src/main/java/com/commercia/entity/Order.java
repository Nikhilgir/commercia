package com.commercia.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.commercia.entity.enums.OrderStatus;
import com.commercia.entity.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer orderId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;

	@ManyToOne
	@JoinColumn(name = "address_id", nullable = false)
	private Addresses deliveryAddress;

	@Column(name = "total_amount", nullable = false)
	private Double totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", columnDefinition = "ENUM('PENDING', 'CONFIRMED', 'PREPARING', 'OUT_FOR_DELIVERY', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING'")
	private OrderStatus orderStatus = OrderStatus.PENDING;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", columnDefinition = "ENUM('PENDING', 'PAID', 'FAILED') DEFAULT 'PENDING'")
	private PaymentStatus paymentStatus = PaymentStatus.PENDING;

	@Column(name = "order_time", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime orderTime;

	@Column(name = "delivery_time")
	private LocalDateTime deliveryTime;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems;
}