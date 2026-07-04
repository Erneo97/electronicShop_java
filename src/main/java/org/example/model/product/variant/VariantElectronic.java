package org.example.model.product.variant;

import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class VariantElectronic {
    Set<VariantItem> parameters = new LinkedHashSet<>();

    public void addOrChangeParameterToVariant(VariantItem items) {
        parameters.stream().filter(parameters -> parameters.parameter().equals(items.parameter())).findFirst().ifPresent(parameters::remove);
        parameters.add(items);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VariantElectronic that = (VariantElectronic) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return parameters.stream().map(Object::hashCode).reduce(0, Integer::sum);
    }
}
