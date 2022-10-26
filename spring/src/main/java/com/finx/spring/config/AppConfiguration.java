package com.finx.spring.config;

import com.finx.mappers.ProductMapper;
import com.finx.mappers.impl.ProductMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapperImpl();
    }
}
