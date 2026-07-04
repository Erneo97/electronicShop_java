package org.example.model.product;

import lombok.Data;

@Data
public abstract class Product {
    private final String id;
    private String name, description;
    private TypeProduct type = TypeProduct.ELECTRONICS;
}
