package org.example.model.cart;

import org.example.model.product.variant.Variant;

public record CartProduct(String id, String name, String description, long quantity, Variant selectedVariant) {
}
