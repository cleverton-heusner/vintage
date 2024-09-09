package com.vintage.infrastructure.adapter.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CustomerDto(@JsonProperty("nome") String name,
                          String cpf,
                          @JsonProperty("compras") List<CustomerPurchaseDto> purchases) {}
