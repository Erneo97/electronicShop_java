package org.example.model.cart;

import lombok.Getter;
import lombok.Setter;
import org.example.model.product.Product;
import org.example.model.product.variant.Variant;

@Getter
@Setter
public class CartProduct extends Product {
    private final Variant variant;
    long quantity;

    public CartProduct(String id, String name, String description, long quantity, Variant selectedVariant) {
        super(id);
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.variant = selectedVariant;
    }
}
