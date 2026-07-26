package org.example.model.order;

import org.example.model.discount.Discount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record Order(int idUser ,List<OrderItem> products, LocalDateTime createdAt, BigDecimal price, List<Discount> discounts) {
}
