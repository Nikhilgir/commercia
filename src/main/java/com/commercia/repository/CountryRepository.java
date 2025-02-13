package com.commercia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commercia.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
	public Optional<Country> findBycountryName(String countryName);
}