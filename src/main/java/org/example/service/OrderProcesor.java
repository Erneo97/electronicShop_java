package org.example.service;

import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;
import org.example.model.product.Product;
import org.example.service.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderProcesor {
    ProductRepository productRepository;
    private final List<Order> orders = new ArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public OrderProcesor(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Future<Order> makeOrder(Order order) {
        return executorService.submit(() -> {
            if (!validateOrder(order)) {
                // TODO: wyjątek nie można złożyć zamówienia
            }
            orders.add(order);
            return order;
        });
    }

    private boolean validateOrder(Order order) {
        return order.products().stream().allMatch(this::validateOrderItemCorrectness);
    }

    private boolean validateOrderItemCorrectness(OrderItem orderProduct) {
        return productRepository.getProductById(orderProduct.idProduct())
                .map(product -> validateAvailableConfigurationProduct(orderProduct, product))
                .orElse(false);
    }

    private boolean validateAvailableConfigurationProduct(OrderItem orderedProduct, Product product) {
        return orderedProduct.parameters().stream()
                .allMatch(parameter -> validateQuantityParametersProduct(product, parameter));
    }

    private boolean validateQuantityParametersProduct(Product product, ParameterOfOrder parameter) {
        return  product.getConfigurationById(parameter.idParameter())
                .map(configuration -> configuration.getQuantity() >= parameter.quantity())
                .orElse(false);
    }

}
