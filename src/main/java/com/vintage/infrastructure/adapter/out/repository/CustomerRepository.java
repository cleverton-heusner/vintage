package com.vintage.infrastructure.adapter.out.repository;

import com.vintage.infrastructure.adapter.out.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;

@Repository
public class CustomerRepository {

    private final String customerUrl;

    public CustomerRepository(@Value("${customer.client.url}") String customerUrl) {
        this.customerUrl = customerUrl;
    }

    public List<CustomerDto> list() {
        final ResponseEntity<List<CustomerDto>> customersResponse = RestClient.create().get()
                .uri(customerUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>(){});
        return customersResponse.getBody();
    }
}