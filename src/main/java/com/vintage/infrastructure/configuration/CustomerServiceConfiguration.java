package com.vintage.infrastructure.configuration;

import com.vintage.domain.service.CustomerService;
import com.vintage.domain.service.ProductService;
import com.vintage.port.out.CustomerApiOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class CustomerServiceConfiguration {

    @Bean
    public CustomerService customerService(final CustomerApiOutputPort customerApiOutputPort,
                                           @Lazy final ProductService productService,
                                           @Value("${loyal.customer.limit}") final int loyalCustomerLimit) {
        return new CustomerService(customerApiOutputPort, productService, loyalCustomerLimit);
    }
}
