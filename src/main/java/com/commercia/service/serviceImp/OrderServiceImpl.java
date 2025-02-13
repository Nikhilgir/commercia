package com.commercia.service.serviceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.commercia.entity.Addresses;
import com.commercia.entity.Order;
import com.commercia.entity.OrderItem;
import com.commercia.entity.Products;
import com.commercia.entity.User;
import com.commercia.entity.enums.OrderStatus;
import com.commercia.entity.enums.PaymentStatus;
import com.commercia.exception.ResourceNotFound;
import com.commercia.exception.SwiggyException;
import com.commercia.repository.AddressesRepository;
import com.commercia.repository.OrderItemRepository;
import com.commercia.repository.OrderRepository;
import com.commercia.repository.ProductRepository;
import com.commercia.repository.RestaurantRepository;
import com.commercia.repository.UserRepository;
import com.commercia.request.OrderItemDTO;
import com.commercia.request.OrderRequest;
import com.commercia.response.AddressResponseDto;
import com.commercia.response.OrderResponseDTO;
import com.commercia.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final RestaurantRepository restaurantRepository;
	private final AddressesRepository addressesRepository;

	public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			ProductRepository productRepository, UserRepository userRepository,
			RestaurantRepository restaurantRepository, AddressesRepository addressesRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.restaurantRepository = restaurantRepository;
		this.addressesRepository = addressesRepository;
	}

	@Transactional
	@Override
	public OrderResponseDTO placeOrder(OrderRequest orderRequest) {
		User user = userRepository.findById(orderRequest.getUserId())
				.orElseThrow(() -> new ResourceNotFound("User not found"));

		var restaurant = restaurantRepository.findById(orderRequest.getRestaurantId())
				.orElseThrow(() -> new ResourceNotFound("Restaurant not found"));

		// Fetch delivery address
		Addresses address = addressesRepository.findById(orderRequest.getAddressId())
				.orElseThrow(() -> new ResourceNotFound("Address not found"));

		List<OrderItem> orderItems = new ArrayList<>();
		double totalAmount = 0.0;

		for (OrderItemDTO item : orderRequest.getItems()) {
			Products product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new ResourceNotFound("Product not found"));

			double itemTotal = product.getPrice() * item.getQuantity();
			totalAmount += itemTotal;

			orderItems.add(new OrderItem(null, null, product, item.getQuantity(), product.getPrice()));
		}

		Order order = new Order();
		order.setUser(user);
		order.setRestaurant(restaurant);
		order.setDeliveryAddress(address);
		order.setTotalAmount(totalAmount);
		order.setOrderStatus(OrderStatus.PENDING);
		order.setPaymentStatus(PaymentStatus.PENDING);
		order.setOrderTime(LocalDateTime.now());

		order = orderRepository.save(order);

		for (OrderItem orderItem : orderItems) {
			orderItem.setOrder(order);
		}
		orderItemRepository.saveAll(orderItems);

		return new OrderResponseDTO(order.getOrderId(), order.getTotalAmount(), order.getOrderStatus(),
				order.getPaymentStatus(),
				new AddressResponseDto(order.getDeliveryAddress().getId(), order.getDeliveryAddress().getHouseNo(),
						order.getDeliveryAddress().getLandMark(), order.getDeliveryAddress().getCity(),
						order.getDeliveryAddress().getState(), order.getDeliveryAddress().getZipCode(),
						order.getDeliveryAddress().getCountry()));
	}

	@Override
	public List<OrderResponseDTO> getOrdersByUserId(int userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFound("User not found with ID: " + userId));
		List<Order> orders = orderRepository.findByUser(user);
		// Convert to DTOs and return
		return orders.stream().map(order -> {
			return new OrderResponseDTO(order.getOrderId(), order.getTotalAmount(), order.getOrderStatus(),
					order.getPaymentStatus(),
					new AddressResponseDto(order.getDeliveryAddress().getId(), order.getDeliveryAddress().getHouseNo(),
							order.getDeliveryAddress().getLandMark(), order.getDeliveryAddress().getCity(),
							order.getDeliveryAddress().getState(), order.getDeliveryAddress().getZipCode(),
							order.getDeliveryAddress().getCountry()));
		}).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public String updateOrderStatus(int orderId, OrderStatus status) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFound("Order not found with ID: " + orderId));

		if (order.getOrderStatus() == OrderStatus.DELIVERED || order.getOrderStatus() == OrderStatus.CANCELLED) {
			throw new SwiggyException("Cannot update order status after it is delivered or cancelled.");
		}
		order.setOrderStatus(status);
		orderRepository.save(order);
		return "Order status updeted sucessfully, status: " + status;
	}

	@Transactional
	@Override
	public String updatePaymentStatus(int orderId, PaymentStatus status) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFound("Order not found with ID: " + orderId));

		if (order.getPaymentStatus() == PaymentStatus.PAID && status == PaymentStatus.PENDING) {
			throw new SwiggyException("Cannot revert payment status from PAID to PENDING.");
		}

		if (order.getOrderStatus() == OrderStatus.CANCELLED && status == PaymentStatus.PAID) {
			throw new SwiggyException("Cannot mark payment as PAID for a cancelled order.");
		}

		order.setPaymentStatus(status);
		orderRepository.save(order);
		return "Payment status updeted sucessfully, status: " + status;
	}
}