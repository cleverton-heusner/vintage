package com.vintage.infrastructure.adapter.out;

import com.vintage.domain.model.Product;
import com.vintage.infrastructure.adapter.out.dto.ProductDto;
import com.vintage.infrastructure.adapter.out.mapper.ProductMapper;
import com.vintage.infrastructure.adapter.out.repository.ProductRepository;
import com.vintage.port.out.ProductApiOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductApiOutputAdapter implements ProductApiOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> list() {
        final List<ProductDto> productsDto = productRepository.list();
        return productsDto.stream().map(productMapper::toProduct).toList();
    }
}