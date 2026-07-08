package org.example.model.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimpleProduct extends Product {
    private BigDecimal price;

    public SimpleProduct(String id) {
        super(id);
    }
}
