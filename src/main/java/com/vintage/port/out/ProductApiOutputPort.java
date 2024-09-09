package com.vintage.port.out;

import com.vintage.domain.model.Product;

import java.util.List;

public interface ProductApiOutputPort {
    List<Product> list();
}
