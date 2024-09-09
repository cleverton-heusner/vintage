package com.vintage.infrastructure.adapter.out.mapper;

import com.vintage.domain.model.Product;
import com.vintage.infrastructure.adapter.out.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "vintage", target = "vintage"),
            @Mapping(source = "purchaseYear", target = "purchaseYear")
    })
    Product toProduct(final ProductDto productDto);
}
