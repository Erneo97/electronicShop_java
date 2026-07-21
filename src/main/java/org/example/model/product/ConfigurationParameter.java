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
