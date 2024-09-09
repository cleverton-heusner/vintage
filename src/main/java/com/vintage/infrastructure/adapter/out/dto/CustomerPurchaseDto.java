package com.vintage.infrastructure.adapter.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerPurchaseDto(@JsonProperty("codigo") String code,
                                  @JsonProperty("quantidade") int quantity) {}
