package com.swiggy.newswiggy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiggy.newswiggy.entity.Addresses;

public interface AddressesRepository extends JpaRepository<Addresses, Integer> {

}
