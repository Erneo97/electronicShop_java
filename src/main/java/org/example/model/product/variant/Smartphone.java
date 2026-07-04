package org.example.model.product.variant;

import org.example.model.product.TypeProduct;
import org.example.model.product.VariantProduct;

public class Smartphone extends VariantProduct {
    public Smartphone(String id, String name) {
        super(id);
        this.name = name;
        this.type = TypeProduct.SMARTPHONE;
    }
}
