package org.example.model.cart;

import java.math.BigDecimal;

public interface Shoppable {
    CartItem addToCart();

    BigDecimal getTotalPrice();
}
