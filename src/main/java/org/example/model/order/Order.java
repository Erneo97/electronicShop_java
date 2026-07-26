package org.example.model.order;

import org.example.model.discount.Discount;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record Order(int idUser , List<OrderItem> products, ZonedDateTime createdAt, BigDecimal price, List<Discount> discounts) {
}
