package com.rcs.inventoryservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
  @Value("baseUrl")
  private String baseUrl;

  @Bean
  public RestTemplate restTemplate(){
    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.rootUri(baseUrl)
            .build();
        return restTemplate;
  }

}
