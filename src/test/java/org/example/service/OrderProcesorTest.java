package org.example.service;

import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;
import org.example.model.order.exeptions.ProductNotExistsExeptions;
import org.example.model.order.exeptions.SelectedParametersNotAvaliableExeption;
import org.example.model.product.Product;
import org.example.model.product.ProductConfiguration;
import org.example.service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderProcesorTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderProcesor orderProcessor;

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        Order order = getOrderProductNotExist();

        when(productRepository.getProductById(11))
                .thenReturn(Optional.empty());

        Future<Order> future = orderProcessor.makeOrder(order);

        assertThatThrownBy(future::get)
                .hasCauseInstanceOf(ProductNotExistsExeptions.class);
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsTooLow() {
        when(productRepository.getProductById(1))
                .thenReturn(Optional.of(getOrderProductOrderQuantity0()));

        Order order = createOrderWithQuantity(5);

        Future<Order> future = orderProcessor.makeOrder(order);

        assertThatThrownBy(future::get)
                .hasCauseInstanceOf(SelectedParametersNotAvaliableExeption.class);
    }

    private Order createOrderWithQuantity(int quantity) {
        return new Order(0,
                List.of(
                        new OrderItem(
                                1,
                                List.of(new ParameterOfOrder(1, quantity)))
                ),
                ZonedDateTime.now(),
                BigDecimal.valueOf(123),
                List.of());
    }

    private Product getOrderProductOrderQuantity0() {
        Product producQuantity = new Product(
                "Nowy produkt",
                BigDecimal.ZERO
        );
        producQuantity.addConfiguration(
                new ProductConfiguration()
        );
        return producQuantity;
    }

    private Order getOrderProductNotExist() {
        return new Order(0,
                List.of(
                        new OrderItem(
                                11,
                                List.of(new ParameterOfOrder(1, 1)))
                ),
                ZonedDateTime.now(),
                BigDecimal.valueOf(123),
                List.of());
    }

}
