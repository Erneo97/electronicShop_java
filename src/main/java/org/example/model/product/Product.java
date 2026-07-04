package org.example.model.product;

import lombok.Data;

@Data
public abstract class Product {
    protected final String id;
    protected String name, description;
    protected TypeProduct type = TypeProduct.ELECTRONICS;
}
