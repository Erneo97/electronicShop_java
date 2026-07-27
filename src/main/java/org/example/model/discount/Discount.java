package org.example.model.discount;

import lombok.EqualsAndHashCode;
import org.example.model.product.Product;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * A class representing promotions. Allows you to include a new price according to promotionSize, provided the promotion is current or the product meets the conditions in discountDueCondition.
 */
@EqualsAndHashCode
public class Discount {
    private final Function<Product, Boolean> discountDueCondition;
    private final Function<Product, BigDecimal> promotionSize;
    private final String name;

    public Discount(String name, Function<Product, Boolean> discountDueCondition, Function<Product, BigDecimal> promotionSize) {
        this.discountDueCondition = discountDueCondition;
        this.promotionSize = promotionSize;
        this.name = name;
    }

    public BigDecimal applyDiscount(Product product) {
        if (discountDueCondition.apply(product)) {
            return promotionSize.apply(product);
        }
        return product.getTotalPrice();
    }

    @Override
    public String toString() {
        return name;
    }
}
