package com.vintage.domain.model;

import java.math.BigDecimal;

public record LoyalCustomer(String cpf, String name, BigDecimal totalPurchases) {}
