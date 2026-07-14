package org.example.model.cart;

import org.example.model.product.variant.Variant;

public record CartItem (int id, String name, String description, long quantity, Variant selectedVariant) {
}
