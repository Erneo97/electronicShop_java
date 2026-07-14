package org.example.model.cart;

import org.example.model.product.ProductConfiguration;

public interface Shoppable {
    CartItem addToCart(ProductConfiguration configuration);
    boolean checkProductVariantExists(ProductConfiguration configuration);
}
