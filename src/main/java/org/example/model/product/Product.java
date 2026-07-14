package org.example.model.product;

import lombok.Data;
import lombok.NonNull;
import org.example.model.cart.CartItem;
import org.example.model.cart.Shoppable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Product implements Shoppable {
    private static AtomicInteger counterID = new AtomicInteger(0);
    private final int id = counterID.getAndIncrement();
    @NonNull
    protected String name, description = "'brak opisu'";
    protected TypeProduct type = TypeProduct.ELECTRONICS;

    private final Set<ProductConfiguration> variants = new HashSet<>();

    public void addVariant(ProductConfiguration variant) {
        this.variants.add(variant);
    }

    @Override
    public CartItem addToCart(ProductConfiguration configuration) {
        return new CartItem(id, name, description, 1, configuration);
    }

    public boolean checkProductVariantExists(ProductConfiguration configuration) {
        return variants.contains(configuration);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        AtomicInteger index = new AtomicInteger(1);
        sb.append(String.format("'%s' (%s) Opis: %s\n", name, id, description));
        variants.forEach(
                variant -> {
                    sb.append(String.format("\tWariant %d:  %s\n", index.getAndIncrement(), variant));
                }
        );
        return sb.toString();
    }

}
