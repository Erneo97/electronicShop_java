package org.example.model.cart;

import org.example.model.order.OrderItem;
import org.example.model.order.Orderlable;
import org.example.model.order.ParameterOfOrder;
import org.example.model.product.ProductConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Cart implements Orderlable {
    private final List<CartItem> products = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void addToCart(Shoppable product, ProductConfiguration configuration) {
        if (!product.checkProductVariantExists(configuration)) {
            return; // TODO: throw un check
        }
        products.add(product.addToCart(configuration));
        totalPrice = totalPrice.add(configuration.getTotalPrice());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        sb.append("Twój koszyk :\n");
        products.forEach(product -> {
            sb.append(String.format("%3d) %3d szt. %s opis: %s\n\t%s\n",
                    index.getAndIncrement(),
                    product.quantity(),
                    product.name(),
                    product.description(),
                    product.selectedVariant()));
        });
        sb.append(String.format("Całkowita cena: %.2f\n", totalPrice));
        return sb.toString();
    }

    public void clearCart() {
        products.clear();
        totalPrice = BigDecimal.ZERO;
    }

    @Override
    public List<OrderItem> getProductsToOrder() {
        return products.stream()
                .map(product -> new OrderItem(product.id(), getParameters(product)))
                .toList();
    }

    private List<ParameterOfOrder> getParameters(CartItem product) {
        return product.selectedVariant().getParameters().stream()
                .map(parameter -> new ParameterOfOrder(parameter.getId(), parameter.getQuantity()))
                .toList();
    }
}
