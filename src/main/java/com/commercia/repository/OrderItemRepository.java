package com.commercia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercia.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
