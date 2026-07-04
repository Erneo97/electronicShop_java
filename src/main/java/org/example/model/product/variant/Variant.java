package org.example.model.product.variant;

import java.util.*;

import lombok.Getter;

@Getter
public class Variant {
    private final Set<VariantItem> parameters = new LinkedHashSet<>();

    public void addOrChangeParameterToVariant(VariantItem items) {
        parameters.stream().filter(parameters -> parameters.parameter().equals(items.parameter())).findFirst().ifPresent(parameters::remove);
        parameters.add(items);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        parameters.forEach(parameter -> {
            sb.append(String.format("%10s: %5s; ", parameter.parameter(), parameter.value()));
        });
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Variant that = (Variant) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return parameters.stream().map(Object::hashCode).reduce(0, Integer::sum);
    }
}
