package com.vintage.infrastructure.adapter.out.repository;

import com.vintage.infrastructure.adapter.out.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;

@Repository
public class ProductRepository {

    private final String productUrl;

    public ProductRepository(@Value("${product.client.url}") final String productUrl) {
        this.productUrl = productUrl;
    }

    public List<ProductDto> list() {
        final ResponseEntity<List<ProductDto>> productsResponse = RestClient.create().get()
                .uri(productUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>(){});
        return productsResponse.getBody();
    }
}