package com.aoa;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AcknowledgementConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
