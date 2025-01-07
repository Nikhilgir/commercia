package com.swiggy.newswiggy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiggy.newswiggy.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
	public Optional<Country> findBycountryName(String countryName);
}