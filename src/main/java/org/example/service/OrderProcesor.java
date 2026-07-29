package org.example.service;

import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;
import org.example.model.order.exeptions.ProductNotExists;
import org.example.model.order.exeptions.SelectedParametersNotAvaliableExeption;
import org.example.model.product.Product;
import org.example.service.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class OrderProcesor {
    private final ProductRepository productRepository;
    private final List<Order> orders = new ArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public OrderProcesor(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Future<Order> makeOrder(Order order) {
        return executorService.submit(() -> {
            validateOrder(order);
            orders.add(order);
            return order;
        });
    }

    private void validateOrder(Order order) throws ProductNotExists, SelectedParametersNotAvaliableExeption {
        List<OrderItem> aggregatedOrderedProducts = aggregateOrderItems(order.products());
        for (OrderItem orderItem : aggregatedOrderedProducts) {
            Product existedProduct = validateProductsInOrderAndGet(orderItem);
            validateAvailableConfigurationProduct(orderItem, existedProduct);
        }

    }

    private List<OrderItem> aggregateOrderItems(List<OrderItem> orderItems) {
        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::idProduct))
                .entrySet()
                .stream()
                .map(entry -> new OrderItem(
                        entry.getKey(),
                        aggregateParameters(entry.getValue())
                ))
                .toList();
    }

    private List<ParameterOfOrder> aggregateParameters(List<OrderItem> orderItems) {
        return orderItems.stream()
                .flatMap(orderItem -> orderItem.parameters().stream())
                .collect(Collectors.groupingBy(
                        ParameterOfOrder::idParameter,
                        Collectors.summingInt(ParameterOfOrder::quantity)
                ))
                .entrySet()
                .stream()
                .map(entry -> new ParameterOfOrder(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();
    }

    private Product validateProductsInOrderAndGet(OrderItem orderItem) throws ProductNotExists {
        Optional<Product> optProduct = productRepository.getProductById(orderItem.idProduct());
        if (optProduct.isEmpty()) {
            throw new ProductNotExists(String.format("Produkt z id %s nie znaleziony", orderItem.idProduct()));
        }
        return optProduct.get();
    }


    private void validateAvailableConfigurationProduct(OrderItem orderedProduct, Product product) throws SelectedParametersNotAvaliableExeption {
        for (ParameterOfOrder parameterOfOrder : orderedProduct.parameters()) {
            if (!validateQuantityParametersProduct(product, parameterOfOrder)) {
                throw new SelectedParametersNotAvaliableExeption(
                        String.format("Product id: %s nie posiada wystarczającej ilości konfiguracji o id %s", product.getId(), parameterOfOrder.idParameter())
                );
            }
        }
    }

    private boolean validateQuantityParametersProduct(Product product, ParameterOfOrder parameter) {
        return product.getConfigurationById(parameter.idParameter())
                .map(configuration -> configuration.getQuantity() >= parameter.quantity())
                .orElse(false);
    }
}
