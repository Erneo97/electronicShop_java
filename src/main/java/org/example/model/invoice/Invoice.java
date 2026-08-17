package org.example.model.invoice;

import org.example.model.order.Order;

public interface Invoice {
    String generateOrderInvoice(Order order);
}
