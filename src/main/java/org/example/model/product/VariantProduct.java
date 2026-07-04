package org.example.model.product;

import lombok.Getter;
import org.example.model.product.variant.VariantElectronic;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class VariantProduct extends Product{
    private final Set<VariantElectronic> variants = new HashSet<>();

    public void addVariant(VariantElectronic variant) {
        this.variants.add(variant);
    }

    public VariantProduct(String id) {
        super(id);
    }
}
