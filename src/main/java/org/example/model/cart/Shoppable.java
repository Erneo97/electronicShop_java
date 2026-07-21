package org.example.model.cart;

import org.example.model.product.ProductConfiguration;

import java.math.BigDecimal;

public interface Shoppable {
    CartItem addToCart();
    public BigDecimal getTotalPrice();
}
