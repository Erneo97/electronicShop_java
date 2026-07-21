package org.example.model.product;

import java.math.BigDecimal;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductConfiguration {
    private final List<ConfigurationParameter> parameters = new ArrayList<>();
    private final Set<TechnicalParameter> categories = new HashSet<>();

    public void addParameterToConfiguration(ConfigurationParameter items) {
        parameters.add(items);
        categories.add(items.getParameter());
    }

    public void addAllParameters(ProductConfiguration productConfiguration) {
        productConfiguration.getParameters().forEach(item -> {
            parameters.add(item);
            categories.add(item.getParameter());
        });
    }

    public Optional<ConfigurationParameter> getParameterById(int id) {
        return parameters.stream()
                .filter(parameter -> parameter.getId() == id)
                .findFirst();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        categories.forEach(category -> {
            sb.append(String.format("%s : ", category));
            sb.append(getParametersCategory(category));
            sb.append("\n");
        });
        return sb.toString();
    }

    private String getParametersCategory(TechnicalParameter category) {
        StringBuffer sb = new StringBuffer();
        parameters.stream().filter(parameter -> parameter.getParameter().equals(category))
                .forEach(foundParameter ->
                        sb.append(foundParameter.getValue()).append(", "));
        return sb.toString();
    }

    public List<ConfigurationParameter> getParametersByCategory(TechnicalParameter category) {
        List<ConfigurationParameter> list = new ArrayList<>();
        parameters.stream().filter(parameter -> parameter.getParameter().equals(category))
                .forEach(foundParameter ->
                        list.add(foundParameter));
        return list;
    }

    public boolean isConfigurationAvailable(ProductConfiguration configuration) { // TODO:
        return this.parameters.stream()
                .allMatch(parameter ->
                        configuration.parameters.stream()
                                .anyMatch(item -> item.getId() == parameter.getId())
                );

    }

    public BigDecimal getTotalPrice() {
        return parameters.stream().map(ConfigurationParameter::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clearConfiguration() {
        parameters.clear();
        categories.clear();
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
