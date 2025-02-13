package com.commercia.service;

import com.commercia.entity.User;
import com.commercia.request.LoginRequest;
import com.commercia.request.SignupRequest;

public interface AuthService {

	public User saveSignupDetails(SignupRequest user);

	public User authenticate(LoginRequest input);
}