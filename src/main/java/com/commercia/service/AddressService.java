package com.commercia.service;

import java.util.List;

import com.commercia.request.AddressRequest;
import com.commercia.response.AddressResponseDto;

public interface AddressService {
	public String addAddress(AddressRequest addressRequest);
	public List<AddressResponseDto> getAddressByUserId(int userId);
	public String deleteAddress(int userId,int addressId);
}
