package org.example.model.cart;


import org.example.model.product.variant.Variant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartProduct> products = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void addToCart(Shoppable product, Variant variant) {
        if (!product.checkProductVariantExists(variant)) {
            return; // TODO: throw un check
        }
        products.add(product.toCartProduct(variant));
        totalPrice = totalPrice.add(variant.getPrice());
    }

    public void removeFromCardByIndex(int index) {
        products.remove(index);
    }
}
