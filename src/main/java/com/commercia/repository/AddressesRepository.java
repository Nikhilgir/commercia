package com.commercia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.commercia.entity.Addresses;

import jakarta.transaction.Transactional;

public interface AddressesRepository extends JpaRepository<Addresses, Integer> {
	
	List<Addresses> findByUserId(int userId);
	
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM user_address WHERE address_id= :addressId AND user_id = :userId", nativeQuery = true)
    int deleteByUserIdAndAddressId(@Param("userId") int userId, @Param("addressId") int addressId);
}
