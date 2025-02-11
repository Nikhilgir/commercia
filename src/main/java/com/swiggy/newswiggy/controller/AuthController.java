package com.swiggy.newswiggy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.newswiggy.entity.User;
import com.swiggy.newswiggy.request.LoginRequest;
import com.swiggy.newswiggy.request.SignupRequest;
import com.swiggy.newswiggy.response.LoginResponse;
import com.swiggy.newswiggy.response.SignupResponse;
import com.swiggy.newswiggy.security.jwt.JwtService;
import com.swiggy.newswiggy.service.AuthService;
import com.swiggy.newswiggy.utils.Endpoints;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user/auth")
@Slf4j
public class AuthController {

	AuthService authService;
	private final JwtService jwtService;

	@Autowired
	public AuthController(AuthService signupService, JwtService jwtService) {
		this.authService = signupService;
		this.jwtService = jwtService;
	}

	@PostMapping(value = Endpoints.SIGNUP, consumes = { "application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<SignupResponse> userSignUp(@Valid @RequestBody SignupRequest signupRequest) {
		log.info("SignUp Start..");
		User user = authService.saveSignupDetails(signupRequest);
		log.info("Sigup Successfull");
		return new ResponseEntity<SignupResponse>(
				new SignupResponse("registered Successfull", HttpStatus.CREATED.name(), HttpStatus.CREATED.value()),
				HttpStatus.CREATED);
	}

	@PostMapping(value = Endpoints.LOGIN, consumes = { "application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {

		log.info("Login started");

		User authenticatedUser = authService.authenticate(loginRequest);
		String jwtToken = jwtService.generateToken(authenticatedUser);
		LoginResponse loginResponse = new LoginResponse();

		loginResponse.setUserId(authenticatedUser.getId());
		loginResponse.setName(authenticatedUser.getFirstName());
		loginResponse.setMessage("Login Successfully");
		loginResponse.setStatus(HttpStatus.OK.name());
		loginResponse.setHttpCode(HttpStatus.OK.value());
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());
		log.info("Login Successfull");
		return ResponseEntity.ok(loginResponse);

	}

//	{
//		  "userName": "nkgiri@gmail.com",
//		  "password": "Nikhil@g@123"
//		}
}
