package com.vintage.infrastructure.adapter.out;

import com.vintage.domain.model.Customer;
import com.vintage.infrastructure.adapter.out.dto.CustomerDto;
import com.vintage.infrastructure.adapter.out.mapper.CustomerMapper;
import com.vintage.infrastructure.adapter.out.repository.CustomerRepository;
import com.vintage.port.out.CustomerApiOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerApiOutputAdapter implements CustomerApiOutputPort {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<Customer> listCustomers() {
        final List<CustomerDto> customersDto = customerRepository.list();
        return customersDto.stream().map(customerMapper::toCustomer).toList();
    }
}