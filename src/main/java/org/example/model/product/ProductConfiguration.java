package org.example.model.product;

import java.math.BigDecimal;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductConfiguration {
    private final List<ConfigurationParameter> parameters = new ArrayList<>();

    public void addOrChangeParameterToVariant(ConfigurationParameter items) {
        parameters.stream().filter(parameters -> parameters.getParameter().equals(items.getParameter())).findFirst().ifPresent(parameters::remove);
        parameters.add(items);
    }

    public void addAllParameters(ProductConfiguration productConfiguration) {
        parameters.addAll(productConfiguration.parameters);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        parameters.forEach(parameter -> {
            sb.append(String.format("%10s: %5s; ", parameter.getParameter().getParameter(), parameter.getValue()));
        });
        return sb.toString();
    }

    public boolean isConfigurationAvaliable(ProductConfiguration configuration) { // TODO:
        return configuration.parameters.contains(this.parameters);
    }

    public BigDecimal getTotalPrice() {
        return parameters.stream().map(ConfigurationParameter::getPrice).reduce(BigDecimal::add).get();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductConfiguration that = (ProductConfiguration) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return parameters.stream().map(Object::hashCode).reduce(0, Integer::sum);
    }
}
