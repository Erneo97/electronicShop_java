package org.example.model.cart;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.example.model.discount.Discount;
import org.example.model.order.OrderItem;
import org.example.model.order.Orderlable;
import org.example.model.order.ParameterOfOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Cart implements Orderlable {
    private final List<CartItem> products = new ArrayList<>();
    @Getter
    private BigDecimal totalPrice = BigDecimal.ZERO;
    @Getter
    private final List<Discount> discounts = new ArrayList<>();
    @Setter
    private BigDecimal discountPrice = BigDecimal.ZERO;

    public void addToCart(@NonNull Shoppable product) {
        products.add(product.addToCart());
        totalPrice = totalPrice.add(product.getTotalPrice());
    }

    public void removeFromCartByIndex(int index) {
        try {
        products.remove(index);
        }catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        AtomicInteger index = new AtomicInteger(1);
        sb.append(String.format("Twój koszyk (%d elem.):\n", products.size()));
        products.forEach(product -> {
            sb.append(String.format("%3d) %3d szt. %s opis: %s\n\t%s\n",
                    index.getAndIncrement(),
                    product.quantity(),
                    product.name(),
                    product.description(),
                    product.selectedVariant()));
        });
        sb.append(String.format("Całkowita cena: %.2f\n", totalPrice));
        sb.append(String.format("Całkowita cena (ze zniżkami): %.2f\n", totalPrice.subtract(discountPrice)));
        return sb.toString();
    }

    @Override
    public List<OrderItem> getProductsToOrder() {
        return products.stream()
                .map(product -> new OrderItem(product.id(), getParameters(product)))
                .toList();
    }

    public void clearCart() {
        products.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private List<ParameterOfOrder> getParameters(CartItem product) {
        return product.selectedVariant().getParameters().stream()
                .map(parameter -> new ParameterOfOrder(parameter.getId(), parameter.getQuantity()))
                .toList();
    }
}
