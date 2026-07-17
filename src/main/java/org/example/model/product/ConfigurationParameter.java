package org.example.model.product;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public record ConfigurationParameter(TechnicalParameter parameter, String value, BigDecimal price, int id) {
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    public ConfigurationParameter(
            TechnicalParameter parameter,
            String value,
            BigDecimal price
    ) {
        this(parameter, value, price, idCounter.getAndIncrement());
    }
}
