package org.example.model.order;

import java.util.List;

public interface Orderlable {
    List<OrderItem> getProductsToOrder();
}