package com.nashtech.rookies.java05.AssetManagement.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MappingData {
    private static ModelMapper modelMapper;
    
    @Autowired
    public MappingData(ModelMapper modelMapper) {
        MappingData.modelMapper = modelMapper;
    }
    
    public static <T, S> S mapping(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }
    
    public static <D, T> List<D> mapList(final List<T> typeList, Class<D> outCLass) {
        return typeList.stream().map(entity -> mapping(entity, outCLass)).collect(Collectors.toList());
    }
}
