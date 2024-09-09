package com.vintage.infrastructure.configuration;

import com.vintage.domain.service.CustomerService;
import com.vintage.domain.service.ProductService;
import com.vintage.port.out.ProductApiOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ProductServiceConfiguration {

    @Bean
    public ProductService productService (@Lazy final CustomerService customerService,
                                          final ProductApiOutputPort productApiOutputPort) {
        return new ProductService(customerService, productApiOutputPort);
    }
}