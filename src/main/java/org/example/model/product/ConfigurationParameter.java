package org.example.model.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public final class ConfigurationParameter {
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final TechnicalParameter parameter;
    private final String value;
    private BigDecimal price;
    private final int id;
    private int quantity;

    /**
     * This class represents a product configuration parameter. It allows you to specify the type of parameter, such as memory size, color, etc., the value it contains, stock status, and the impact of a given parameter on price.
     * @param parameter
     * @param value
     * @param price
     * @param quantity
     */
    public ConfigurationParameter(
            TechnicalParameter parameter,
            String value,
            BigDecimal price,
            int quantity
    ) {
        this.parameter = parameter;
        this.value = value;
        this.price = price;
        this.id = idCounter.getAndIncrement();
        this.quantity = quantity;
    }
}
