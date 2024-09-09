package com.vintage.infrastructure.adapter.out.mapper;

import com.vintage.domain.model.Customer;
import com.vintage.domain.model.CustomerPurchase;
import com.vintage.infrastructure.adapter.out.dto.CustomerDto;
import com.vintage.infrastructure.adapter.out.dto.CustomerPurchaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CustomerMapper {

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "cpf", target = "cpf"),
    })
    Customer toCustomer(final CustomerDto customerDto);

    @Mappings({
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "quantity", target = "quantity"),
    })
    CustomerPurchase toPurchase(final CustomerPurchaseDto customerPurchaseDto);
}
