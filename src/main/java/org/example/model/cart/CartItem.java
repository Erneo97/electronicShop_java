package org.example.model.cart;

import org.example.model.product.ProductConfiguration;

public record CartItem (int id, String name, String description, long quantity, ProductConfiguration selectedVariant) {
}
