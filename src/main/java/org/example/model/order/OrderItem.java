package org.example.model.order;

import org.example.model.product.variant.Variant;


public record OrderItem(int idProduct, int idVariant, int quantity) {
}
