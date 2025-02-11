package com.swiggy.newswiggy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiggy.newswiggy.entity.Order;
import com.swiggy.newswiggy.entity.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByUser(User user);
}
