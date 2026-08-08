package org.example.service;

import org.example.model.discount.Discount;
import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;
import org.example.model.order.exeptions.ProductNotExistsExeptions;
import org.example.model.order.exeptions.SelectedParametersNotAvaliableExeption;
import org.example.model.product.Product;
import org.example.service.repository.ProductRepository;

import java.math.BigDecimal;
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
    private static int counterUsersServed = 0;

    public OrderProcesor(ProductRepository productRepository) {
        this.productRepository = productRepository;
        counterUsersServed++;
    }

    public Future<Order> makeOrder(Order order) {
        return executorService.submit(() -> {
            List<OrderItem> aggregatedOrderedProducts = aggregateOrderItems(order.products());
            validateOrderItems(aggregatedOrderedProducts);
            orders.add(order);
            productRepository.decreaseStock(aggregatedOrderedProducts);
            return order;
        });
    }

    public BigDecimal getTotalDiscount(List<OrderItem> orderItems, List<Discount> discounts) {
        return orderItems.stream().map(orderItem -> {
            Product product = productRepository.getProductById(orderItem.idProduct())
                    .orElseThrow();
            return getTotalDiscountForProduct(product, discounts);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalDiscountForProduct(Product product, List<Discount> discounts) {
        return discounts.stream()
                .map(discount -> product.getTotalPrice()
                            .subtract(discount.applyDiscount(product)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void disconectUser() {
        counterUsersServed--;
        if (counterUsersServed == 0) {
            executorService.shutdown();
        }
    }

    private void validateOrderItems(List<OrderItem> orderItems) throws ProductNotExistsExeptions, SelectedParametersNotAvaliableExeption {
        for (OrderItem orderItem : orderItems) {
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

    private Product validateProductsInOrderAndGet(OrderItem orderItem) throws ProductNotExistsExeptions {
        Optional<Product> optProduct = productRepository.getProductById(orderItem.idProduct());
        if (optProduct.isEmpty()) {
            throw new ProductNotExistsExeptions(String.format("Produkt z id %s nie znaleziony", orderItem.idProduct()));
        }
        return optProduct.get();
    }


    private void validateAvailableConfigurationProduct(OrderItem orderedProduct, Product product) throws SelectedParametersNotAvaliableExeption {
        orderedProduct.parameters().forEach(parameterOfOrder -> {
            if (!validateQuantityParametersProduct(product, parameterOfOrder)) {
                throw new SelectedParametersNotAvaliableExeption(
                        String.format("Product id: %s nie posiada wystarczającej ilości konfiguracji o id %s", product.getId(), parameterOfOrder.idParameter())
                );
            }
        });
    }

    private boolean validateQuantityParametersProduct(Product product, ParameterOfOrder parameter) {
        return product.getConfigurationById(parameter.idParameter())
                .map(configuration -> configuration.getQuantity() >= parameter.quantity())
                .orElse(false);
    }
}
