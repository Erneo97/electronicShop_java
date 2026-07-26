package org.example.service;

import org.example.model.order.Order;
import org.example.model.order.exeptions.OrderCannotFulfiledExeption;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;
import org.example.model.order.exeptions.ProductNotExists;
import org.example.model.product.Product;
import org.example.service.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

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

    private void validateOrder(Order order) throws ProductNotExists {
        validateProductsInOrdeExistsr(order);

    }

    private void validateProductsInOrdeExistsr(Order order) throws ProductNotExists {
        for( OrderItem orderItem : order.products()) {
            Optional<Product> optProduct = productRepository.getProductById(orderItem.idProduct());
            if(optProduct.isEmpty()) {
                throw new ProductNotExists(String.format("Produkt z id %s nie znaleziony", orderItem.idProduct()));
            }
        }
    }

    private boolean validateOrderItemCorrectness(OrderItem orderProduct) {
        return productRepository.getProductById(orderProduct.idProduct())
//               TODO: sprawdzenie czy
                .map(product -> validateAvailableConfigurationProduct(orderProduct, product))
                .orElse(false);
    }

    private boolean validateAvailableConfigurationProduct(OrderItem orderedProduct, Product product) {
        return getParameterForOrderItems(orderedProduct)
                .allMatch(parameter -> validateQuantityParametersProduct(product, parameter));
    }

    private boolean validateQuantityParametersProduct(Product product, ParameterOfOrder parameter) {
        return  product.getConfigurationById(parameter.idParameter())
                .map(configuration -> configuration.getQuantity() >= parameter.quantity())
                .orElse(false);
    }

    private Stream<ParameterOfOrder> getParameterForOrderItems(OrderItem orderedProduct) {
        return orderedProduct.parameters().stream();
    }
}
