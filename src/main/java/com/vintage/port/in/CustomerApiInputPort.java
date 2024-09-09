package com.vintage.port.in;

import com.vintage.domain.model.Customer;
import com.vintage.domain.model.DetailedPurchase;
import com.vintage.domain.model.LoyalCustomer;

import java.util.List;

public interface CustomerApiInputPort {

    List<Customer> listCustomers();
    List<LoyalCustomer> listMostLoyalCustomers();
    List<DetailedPurchase> listPurchases();
    DetailedPurchase getBiggestYearPurchase(final int year);
}
