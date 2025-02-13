package com.commercia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercia.request.AddressRequest;
import com.commercia.response.AddressResponseDto;
import com.commercia.service.AddressService;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
	
	private AddressService addressService;
	
	public AddressController(AddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping(value = "/add-address")
	public String addAddresses(@RequestBody AddressRequest addressRequest) {
		return addressService.addAddress(addressRequest);
	}
	
	@GetMapping(value = "/get-addresses/{userId}")
	public List<AddressResponseDto> getAllAddresses(@PathVariable("userId") int userId){
		return addressService.getAddressByUserId(userId);
	}
	
	@GetMapping(value = "/delete-address/{userId}/{addressId}")
	public String deleteAddress(@PathVariable("userId") int userId, @PathVariable("addressId")int addressId) {
		return addressService.deleteAddress(addressId, userId);
	}
}
