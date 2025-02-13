package com.commercia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercia.entity.Addresses;

public interface AddressesRepository extends JpaRepository<Addresses, Integer> {

}
