package com.swiggy.newswiggy.service.serviceImp;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swiggy.newswiggy.entity.Address;
import com.swiggy.newswiggy.entity.Addresses;
import com.swiggy.newswiggy.entity.Country;
import com.swiggy.newswiggy.entity.User;
import com.swiggy.newswiggy.entity.User.Gender;
import com.swiggy.newswiggy.exception.EmailNotFoundException;
import com.swiggy.newswiggy.exception.InvalidAgeException;
import com.swiggy.newswiggy.repository.AddressesRepository;
import com.swiggy.newswiggy.repository.CountryRepository;
import com.swiggy.newswiggy.repository.UserRepository;
import com.swiggy.newswiggy.request.AddressRequest;
import com.swiggy.newswiggy.request.LoginRequest;
import com.swiggy.newswiggy.request.SignupRequest;
import com.swiggy.newswiggy.service.AuthService;
import com.swiggy.newswiggy.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImp implements AuthService {

	private UserRepository userRepository;
	private AddressesRepository addressesRepository;
	private CountryRepository countryRepository;
	private final PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;

	@Autowired
	public AuthServiceImp(UserRepository userRepository, AddressesRepository addressesRepository,
			CountryRepository countryRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.addressesRepository = addressesRepository;
		this.countryRepository = countryRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public User saveSignupDetails(SignupRequest signupRequest) {
		if (signupRequest == null)
			throw new IllegalArgumentException("User cannot be null");

		User user = new User();
		user.setFirstName(signupRequest.getFirstName());
		user.setLastName(signupRequest.getLastName());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setEmail(signupRequest.getEmail());
		user.setPhoneNumber(signupRequest.getPhoneNumber());

		if (Period.between(signupRequest.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
			throw new InvalidAgeException(ExceptionMessages.INVALID_AGE_EXCEPTION);
		}
		user.setAge(Period.between(signupRequest.getDateOfBirth(), LocalDate.now()).getYears());
		user.setDateOfBirth(signupRequest.getDateOfBirth());
		user.setGender(Gender.valueOf(signupRequest.getGender().toUpperCase()));

		// JSON Address
		List<Address> addressList = new ArrayList<Address>();
		Address a;
		for (AddressRequest addresses : signupRequest.getAddress()) {
			a = new Address();
			a.setHouseNo(addresses.getHouseNo());
			a.setLandMark(addresses.getLandMark());
			a.setCity(addresses.getCity());
			a.setState(addresses.getState());
			a.setZipCode(addresses.getZipCode());
			addressList.add(a);
		}
		user.setAddress(addressList);
		User u = userRepository.save(user);

		// Table Addresses
		List<Addresses> addressesList = new ArrayList<Addresses>();

		for (AddressRequest addresses : signupRequest.getAddress()) {
			Addresses b = new Addresses();
			b.setHouseNo(addresses.getHouseNo());
			b.setLandMark(addresses.getLandMark());
			b.setCity(addresses.getCity());
			b.setState(addresses.getState());
			b.setZipCode(addresses.getZipCode());
			Country c = new Country();
			c.setCountryName(addresses.getCountry());

			Optional<Country> country = countryRepository.findBycountryName(addresses.getCountry());
			if (!(country.isPresent())) {
				countryRepository.findBycountryName(addresses.getCountry());
				b.setCountry(countryRepository.save(c));
			} else
				b.setCountry(country.get());

			addressesList.add(b);
			b.setUser(user);
			addressesRepository.save(b);
		}
		return u;
	}

	// Login
	public User authenticate(LoginRequest input) {

		boolean b = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getUserName(), input.getPassword()))
				.isAuthenticated();

		log.info("Authentication status is :" + b);

		return userRepository.findByEmail(input.getUserName())
				.orElseThrow(() -> new EmailNotFoundException(ExceptionMessages.EMAIL_NOT_FOUND_EXCEPTION));
	}

	// Get Data by JOIN
	public void getUserDetailsByJoin(String email) {
		List<User> list = userRepository.getUserDetails(email);
		for (User data : list) {
//			System.out.print("Name: " + data.getFirstName() + ", Email: " + data.getEmail() + ", House No: "
//					+ data.getHouseNo() + ", City: " + data.getCity() + ", State: " + data.getState() + "\n");
		}
	}

	public void UpdatePhoneNumber(long phone, String email) {
		userRepository.updatePhoneNumber(phone, email);
	}

	public void findNameAndPhone(String email) {
		log.info("find Name and Phone :");
		Optional<String> us = userRepository.findNameAndPhoneByEmail(email);
		System.out.println(us.get());
	}
}
