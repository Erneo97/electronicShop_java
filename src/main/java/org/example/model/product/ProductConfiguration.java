package org.example.model.product;

import java.math.BigDecimal;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductConfiguration {
    private final List<ConfigurationParameter> parameters = new ArrayList<>();

    public void addParameterToConfiguration(ConfigurationParameter items) {
        parameters.add(items);
    }

    public void addAllParameters(ProductConfiguration productConfiguration) {
        parameters.addAll(productConfiguration.parameters);
    }

    public Optional<ConfigurationParameter> getParameterById(int id) {
        return parameters.stream()
                .filter(parameter -> parameter.getId() == id)
                .findFirst();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(parameters.size() + " parametrów.");
        parameters.forEach(parameter ->
                sb.append(String.format("%10s: %5s; ", parameter.getParameter().getParameter(), parameter.getValue()))
        );
        return sb.toString();
    }

    public boolean isConfigurationAvaliable(ProductConfiguration configuration) { // TODO:
        return this.parameters.stream()
                .allMatch(parameter ->
                        configuration.parameters.stream()
                                .anyMatch(item -> item.getId() == parameter.getId())
                );

    }

    public BigDecimal getTotalPrice() {
        return parameters.stream().map(ConfigurationParameter::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
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
