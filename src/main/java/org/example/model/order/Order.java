package org.example.model.order;

import org.example.model.discount.Discount;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 * @param idUser
 * @param products - list of products that the user wants to order
 * @param createdAt
 * @param price
 * @param discounts - discounts added to your purchase
 */
public record Order(int idUser , List<OrderItem> products, ZonedDateTime createdAt, BigDecimal price, List<Discount> discounts) {
}
