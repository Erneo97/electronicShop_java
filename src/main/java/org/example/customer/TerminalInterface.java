package org.example.customer;

import org.example.model.cart.Cart;
import org.example.model.order.Order;
import org.example.service.manager.ProductManager;
import org.example.model.product.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TerminalInterface {
    private static final AtomicInteger nextId = new AtomicInteger(0);
    private final int userId =  nextId.getAndIncrement();
    private final ProductManager productManager;

    public TerminalInterface(ProductManager productManager) {
        this.productManager = productManager;
    }

    public List<Product> getProducts() {
        return productManager.getProducts();
    }

    public void addProduct(Product product) {
        productManager.addProduct(product);
    }

    public void removeProduct(Product product) {
        productManager.removeProduct(product);
    }

    public void makeOrder(Cart cart) {
        LocalDateTime createdAt = LocalDateTime.now();
        Order newOrder = new Order(userId, cart.getProductsToOrder(), createdAt);
        // TODO: wysłanie do procesowania
    }
}
