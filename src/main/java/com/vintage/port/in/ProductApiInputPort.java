package com.vintage.port.in;

import com.vintage.domain.model.Product;

public interface ProductApiInputPort {

    Product recommend(final String customerInfo);
}
