package org.example.customer;

import org.example.model.cart.Cart;
import org.example.model.order.Order;
import org.example.service.OrderProcesor;
import org.example.service.manager.ProductManager;
import org.example.model.product.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class TerminalInterface {
    private static final AtomicInteger nextId = new AtomicInteger(0);
    private final int userId =  nextId.getAndIncrement();
    private final ProductManager productManager;
    private final OrderProcesor orderProcesor;

    public TerminalInterface(ProductManager productManager, OrderProcesor orderProcesor) {
        this.productManager = productManager;
        this.orderProcesor = orderProcesor;
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

    public Future<Boolean> makeOrder(Cart cart) {
        // TODO: wyjątek jaka operacja nie udana
        LocalDateTime createdAt = LocalDateTime.now();
        Order newOrder = new Order(userId, cart.getProductsToOrder(), createdAt, cart.getTotalPrice());
        cart.clearCart();
        return orderProcesor.makeOrder(newOrder);
    }
}
