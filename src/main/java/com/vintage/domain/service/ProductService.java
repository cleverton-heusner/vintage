package com.vintage.domain.service;

import com.vintage.domain.exception.ResourceNotFoundException;
import com.vintage.domain.model.Product;
import com.vintage.port.in.ProductApiInputPort;
import com.vintage.port.out.ProductApiOutputPort;

import java.util.List;
import java.util.Optional;

public class ProductService implements ProductApiInputPort {

    private final CustomerService customerService;
    private final ProductApiOutputPort productApiOutputPort;

    public ProductService(final CustomerService customerService,
                          final ProductApiOutputPort productApiOutputPort) {
        this.customerService = customerService;
        this.productApiOutputPort = productApiOutputPort;
    }

    public List<Product> list() {
        return productApiOutputPort.list();
    }

    public List<Product> filterByYear(final int year) {
        return list().stream()
                .filter(product -> product.purchaseYear() == year)
                .toList();
    }

    public Product recommend(final String customerInfo) {
        final Optional<String> biggestPurchaseCodeOptional = customerService.findBiggestPurchaseCodeFrom(customerInfo);

        if (biggestPurchaseCodeOptional.isPresent()) {
            final Optional<Product> recommendedProductOptional = find(biggestPurchaseCodeOptional.get());
            if (recommendedProductOptional.isPresent()) {
                return recommendedProductOptional.get();
            }

            throw new ResourceNotFoundException("Produto não encontrado.");
        }

        throw new ResourceNotFoundException("Cliente não encontrado.");
    }

    private Optional<Product> find(final String code) {
        return list().stream()
                .filter(product -> product.code() == Integer.parseInt(code))
                .findFirst();
    }
}