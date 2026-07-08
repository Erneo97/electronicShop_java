package org.example.model.order;

import org.example.model.product.variant.Variant;


public record OrderItem(String id, String name, String description, long quantity, Variant selectedVariant) {
}
