package org.example.service;

import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.product.Product;
import org.example.service.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderProcesor {
    ProductRepository productRepository;
    private final List<Order> orders = new ArrayList<>();

    public OrderProcesor(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Future<Boolean> makeOrder(Order order) {
        return executorService.submit(() -> {
            boolean validateResult = validateOrder(order);
            if (validateResult) {
                orders.add(order);
            }
            return validateResult;
        });
    }

    private boolean validateOrder(Order order) {

        return false;
    }
}
