package com.vintage.infrastructure.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CustomMonetaryAmount {

    @JsonProperty("amount")
    BigDecimal amount;

    @JsonProperty("currency")
    String currency;

}
