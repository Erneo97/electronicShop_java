package org.example.model.order;

import java.time.LocalDateTime;
import java.util.List;

public record Order(int idUser ,List<OrderItem> products, LocalDateTime createdAt) {
}
