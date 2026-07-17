package org.example.model.cart;

import org.example.model.product.ProductConfiguration;

import java.math.BigDecimal;

public interface Shoppable {
    CartItem addToCart(ProductConfiguration configuration);
    boolean checkProductVariantExists(ProductConfiguration configuration);
    public BigDecimal getPrice();
}
