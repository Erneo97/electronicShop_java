package org.example.model.discount;

import org.example.model.product.Product;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * A class representing promotions. Allows you to include a new price according to promotionSize, provided the promotion is current or the product meets the conditions in discountDueCondition.
 */
public class Discount {
    private final Function<Product, Boolean> discountDueCondition;
    private final Function<Product, BigDecimal> promotionSize;

    public Discount(Function<Product, Boolean> discountDueCondition, Function<Product, BigDecimal> promotionSize) {
        this.discountDueCondition = discountDueCondition;
        this.promotionSize = promotionSize;
    }

    public BigDecimal applyDiscount(Product product) {
        if (discountDueCondition.apply(product)) {
            return promotionSize.apply(product);
        }
        return product.getTotalPrice();
    }
}
