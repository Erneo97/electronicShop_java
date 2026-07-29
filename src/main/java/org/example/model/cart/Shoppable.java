package org.example.model.cart;

import java.math.BigDecimal;

/**
 * Interface allowing you to purchase a given item/add it to your cart
 */
public interface Shoppable {
    CartItem addToCart();

    BigDecimal getTotalPrice();
}
