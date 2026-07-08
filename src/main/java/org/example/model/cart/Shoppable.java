package org.example.model.cart;

import org.example.model.order.OrderItem;
import org.example.model.product.variant.Variant;

public interface Shoppable {
    OrderItem addToCart(Variant variant);
    boolean checkProductVariantExists(Variant variant);
}
