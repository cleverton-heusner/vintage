package com.vintage.infrastructure.adapter.in.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PurchaseYearValidator implements ConstraintValidator<PurchaseYear, Integer> {

    @Override
    public boolean isValid(final Integer purchaseYear, final ConstraintValidatorContext ctx) {
        return purchaseYear != null &&
                purchaseYear >= 1900 &&
                purchaseYear <= LocalDate.now().getYear();
    }
}