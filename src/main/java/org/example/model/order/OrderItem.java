package org.example.model.order;

import java.util.List;

public record OrderItem(int idProduct, List<ParameterOfOrder> parameters) {
}
