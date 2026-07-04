package org.example.model.cart;


import org.example.model.product.variant.Variant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        sb.append("Twój koszyk :\n");
        products.forEach(product -> {
            sb.append(String.format("%3d) %3d szt. %s opis: %s\n\t%s\n",
                    index.getAndIncrement(),
                    product.getQuantity(),
                    product.getName(),
                    product.getDescription(),
                    product.getVariant()));
        });
        sb.append(String.format("Całkowita cena: %.2f\n",totalPrice));
        return sb.toString();
    }
}
