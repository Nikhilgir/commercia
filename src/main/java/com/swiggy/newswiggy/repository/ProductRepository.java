package com.swiggy.newswiggy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swiggy.newswiggy.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

}