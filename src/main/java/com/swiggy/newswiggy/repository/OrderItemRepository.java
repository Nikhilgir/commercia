package com.swiggy.newswiggy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiggy.newswiggy.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
