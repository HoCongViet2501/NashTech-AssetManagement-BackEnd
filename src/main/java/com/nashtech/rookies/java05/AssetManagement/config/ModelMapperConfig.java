package com.nashtech.rookies.java05.AssetManagement.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
 	@Bean
  public ModelMapper modelMapper() {
      return new ModelMapper();
  }
}
