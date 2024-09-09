package com.vintage.domain.model;

import java.util.List;

public record Customer(String name, String cpf, List<CustomerPurchase> purchases) {}