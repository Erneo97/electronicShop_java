package org.example.model.product;

import lombok.Getter;
import org.example.model.product.variant.Variant;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public abstract class VariantProduct extends Product{
    private final Set<Variant> variants = new HashSet<>();

    public void addVariant(Variant variant) {
        this.variants.add(variant);
    }

    public VariantProduct(String id) {
        super(id);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        AtomicInteger index = new AtomicInteger(1);
        sb.append(super.toString());
        variants.forEach(
                variant -> {
                    sb.append(String.format("\tWariant %d:  %s\n", index.getAndIncrement(), variant));
                }
        );
        return sb.toString();
    }
}
