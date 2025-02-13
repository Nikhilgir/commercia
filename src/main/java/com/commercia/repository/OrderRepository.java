package com.commercia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercia.entity.Order;
import com.commercia.entity.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByUser(User user);
}
