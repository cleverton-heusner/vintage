package com.vintage.domain.model;

import java.math.BigDecimal;

public record Product(int code, String type, BigDecimal price, String vintage, int purchaseYear) {}