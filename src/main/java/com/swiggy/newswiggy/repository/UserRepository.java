package com.swiggy.newswiggy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swiggy.newswiggy.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByEmail(String email);

	@Query(value = "SELECT u.firstName,u.phoneNumber FROM User u WHERE email= :email")
	public Optional<String> findNameAndPhoneByEmail(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE email= :email")
	public Optional<User> getUserByEmail(@Param("email") String email);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.phoneNumber= :phone WHERE u.email= :email")
	public void updatePhoneNumber(@Param("phone") long phone, @Param("email") String email);

	@Modifying
	@Transactional
	@Query("INSERT INTO User(firstName,lastName,password,email,phoneNumber,dateOfBirth)"
			+ " VALUES (:#{#user.firstName},:#{#user.lastName},:#{#user.password},:#{#user.email},:#{#user.phoneNumber},:#{#user.dateOfBirth})")
	public int insertDataManually(@Param("user") User user);

	@Query(value = "SELECT u.first_name,a.city FROM user_details u JOIN user_address a ON u.id=a.user_id", nativeQuery = true)
	public List<User> getUserDetails(@Param("email") String email);

}