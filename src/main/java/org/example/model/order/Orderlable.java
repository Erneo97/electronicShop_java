package org.example.model.order;

import java.util.List;

public interface Orderlable {
    public List<OrderItem> getProductsToOrder();
}