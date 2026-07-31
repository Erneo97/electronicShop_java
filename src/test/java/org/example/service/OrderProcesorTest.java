package org.example.service;

import org.example.model.order.Order;
import org.example.model.order.OrderItem;
import org.example.model.order.ParameterOfOrder;
import org.example.model.order.exeptions.ProductNotExistsExeptions;
import org.example.model.product.Product;
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
