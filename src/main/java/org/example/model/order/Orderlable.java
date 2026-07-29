package org.example.model.order;

import java.util.List;

/**
 * An interface that allows you to give a given class the ability to format data for placing an order
 */
public interface Orderlable {
    List<OrderItem> getProductsToOrder();
}