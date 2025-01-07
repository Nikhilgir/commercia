package com.swiggy.newswiggy.service;

import com.swiggy.newswiggy.entity.User;
import com.swiggy.newswiggy.request.LoginRequest;
import com.swiggy.newswiggy.request.SignupRequest;

public interface AuthService {

	public User saveSignupDetails(SignupRequest user);

	public User authenticate(LoginRequest input);
}