package com.commercia.service.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.commercia.entity.Addresses;
import com.commercia.entity.Country;
import com.commercia.entity.User;
import com.commercia.exception.CommerciaException;
import com.commercia.exception.ResourceNotFound;
import com.commercia.repository.AddressesRepository;
import com.commercia.repository.CountryRepository;
import com.commercia.repository.UserRepository;
import com.commercia.request.AddressRequest;
import com.commercia.response.AddressResponseDto;
import com.commercia.service.AddressService;

@Service
public class AddressServiceImp implements AddressService{
	
	private AddressesRepository addressesRepository;
	private UserRepository userRepository;
	private CountryRepository countryRepository;
	
	public AddressServiceImp(AddressesRepository addressesRepository,UserRepository userRepository,CountryRepository countryRepository) {
		this.addressesRepository=addressesRepository;
		this.userRepository=userRepository;
		this.countryRepository=countryRepository;
	}

	@Override
	public String addAddress(AddressRequest addressRequest) {
		User user=userRepository.findById(addressRequest.getUserId()).orElseThrow(()-> new ResourceNotFound("User does't exist"));
		if(user.getAddresses().size()>=3) {
			throw new CommerciaException("You can't add more than 3 addresses");
		}
		Country country=countryRepository.findBycountryName(addressRequest.getCountry()).orElseThrow(()-> new ResourceNotFound("Country not found"));
		Addresses address=new Addresses();
		address.setHouseNo(addressRequest.getHouseNo());
		address.setLandMark(addressRequest.getLandMark());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setZipCode(addressRequest.getZipCode());
		address.setCountry(country);
		address.setUser(user);
		addressesRepository.save(address);
		return "Address saved successfully";
	}

	@Override
	public List<AddressResponseDto> getAddressByUserId(int userId) {
		List<Addresses> addresses=addressesRepository.findByUserId(userId);
		return addresses.stream().map((address)->{
			return new AddressResponseDto(address.getId(),address.getHouseNo(),address.getLandMark(),address.getCity(),address.getState(),address.getZipCode(),address.getCountry());
		}).toList();
	}
	
	@Override
	public String deleteAddress(int userId,int addressId) {
		int isDeleted=addressesRepository.deleteByUserIdAndAddressId(addressId, userId);
		if(isDeleted==1) {
			return "Address delete successfull";
		}else {
			throw new ResourceNotFound("Address Not Found");
		}
		
	}
	
}
