package com.vintage.infrastructure.adapter.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductDto(@JsonProperty("codigo") int code,
                         @JsonProperty("tipo_vinho") String type,
                         @JsonProperty("preco") BigDecimal price,
                         @JsonProperty("safra") String vintage,
                         @JsonProperty("ano_compra") int purchaseYear) {}