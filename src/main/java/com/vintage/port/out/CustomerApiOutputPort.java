package com.vintage.port.out;

import com.vintage.domain.model.Customer;

import java.util.List;

public interface CustomerApiOutputPort {
    List<Customer> listCustomers();
}
