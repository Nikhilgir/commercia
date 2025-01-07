package com.swiggy.newswiggy.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {

	private String firstName;
	private String email;
	private String houseNo;
	private String city;
	private String state;

}
