package org.example.model.cart;

import org.example.model.product.variant.Variant;

public interface Shoppable {
    CartProduct toCartProduct(Variant variant);
    boolean checkProductVariantExists(Variant variant);
}
