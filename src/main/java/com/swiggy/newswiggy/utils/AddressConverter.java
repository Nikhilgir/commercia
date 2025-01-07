package com.swiggy.newswiggy.utils;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.newswiggy.entity.Address;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AddressConverter implements AttributeConverter<List<Address>, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Address> address) {
		try {
			return address != null ? objectMapper.writeValueAsString(address) : null;
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert address to Json", e);
		}
	}

	@Override
	public List<Address> convertToEntityAttribute(String dbData) {
		try {
			return dbData != null ? objectMapper.readValue(dbData, new TypeReference<List<Address>>() {
			}) : null;
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert JSON to address", e);
		}
	}

}
