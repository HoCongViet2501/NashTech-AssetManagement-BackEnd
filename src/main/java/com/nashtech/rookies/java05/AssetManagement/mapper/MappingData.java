package com.nashtech.rookies.java05.AssetManagement.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MappingData {
	private static ModelMapper modelMapper;
	
	@Autowired
	public MappingData(ModelMapper modelMapper) {
		MappingData.modelMapper = modelMapper;
	}
	
	public static  <T, S> S mapToResponse(T data, Class<S> type) {
		return modelMapper.map(data, type);
	}
	
	public static  <T, S> S mapToEntity(T data, Class<S> type) {
		return modelMapper.map(data, type);
	}
}
