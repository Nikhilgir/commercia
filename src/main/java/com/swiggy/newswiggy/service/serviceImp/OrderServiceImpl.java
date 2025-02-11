package com.swiggy.newswiggy.service.serviceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swiggy.newswiggy.entity.Addresses;
import com.swiggy.newswiggy.entity.Order;
import com.swiggy.newswiggy.entity.OrderItem;
import com.swiggy.newswiggy.entity.Products;
import com.swiggy.newswiggy.entity.User;
import com.swiggy.newswiggy.entity.enums.OrderStatus;
import com.swiggy.newswiggy.entity.enums.PaymentStatus;
import com.swiggy.newswiggy.exception.ResourceNotFound;
import com.swiggy.newswiggy.exception.SwiggyException;
import com.swiggy.newswiggy.repository.AddressesRepository;
import com.swiggy.newswiggy.repository.OrderItemRepository;
import com.swiggy.newswiggy.repository.OrderRepository;
import com.swiggy.newswiggy.repository.ProductRepository;
import com.swiggy.newswiggy.repository.RestaurantRepository;
import com.swiggy.newswiggy.repository.UserRepository;
import com.swiggy.newswiggy.request.OrderItemDTO;
import com.swiggy.newswiggy.request.OrderRequest;
import com.swiggy.newswiggy.response.AddressResponseDto;
import com.swiggy.newswiggy.response.OrderResponseDTO;
import com.swiggy.newswiggy.service.OrderService;

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